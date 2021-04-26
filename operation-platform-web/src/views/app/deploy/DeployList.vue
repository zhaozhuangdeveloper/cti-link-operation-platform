<template>
  <div id="deploy-list">
    <div id="header">
      <div style="float: right">
        <el-button type="danger" size="mini">创建应用</el-button>
      </div>
    </div>
    <div id="table" style="border: 1px solid #FFFFFF; padding: 10px; background: #FFFFFF;">
      <el-button type="danger" :disabled="multipleSelection.length === 0" size="mini">删除应用</el-button>
      <el-table
        ref="multipleTable"
        :data="deployments.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        tooltip-effect="dark"
        stripe
        style="width: 100%;"
        :row-key="getRowKey"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"
          reserve-selection>
        </el-table-column>
        <el-table-column
          prop="name"
          label="应用名称">
          <template slot-scope="scope">
            <router-link :to="{name: 'Deployment', params: { namespaceName: scope.row.namespaceName, deploymentName: scope.row.name, activeName: 'pod'}}" style="text-decoration:none; color: #409EFF;">
              {{ scope.row.name }}
            </router-link>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="status"
          label="状态">
        </el-table-column>
        <el-table-column
          align="center"
          label="实例个数(正常/全部)">
          <template slot-scope="scope">{{ scope.row.readyReplicas }}/{{ scope.row.replicas }}</template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="namespaceName"
          label="命名空间">
        </el-table-column>
        <el-table-column
          align="center"
          prop="creationTimestamp"
          label="创建时间">
        </el-table-column>
        <el-table-column
          align="center"
          label="操作">
          <template slot-scope="scope">
            <el-row>
              <el-col :span="4" :offset="6">
                <el-button @click="handleRestart(scope.row)" type="text">重启</el-button>
              </el-col>
              <el-col :span="4">
                <el-button @click="handleUpgrade(scope.row)" type="text">升级</el-button>
              </el-col>
              <el-col :span="6">
                <el-dropdown trigger="click">
                  <el-button class="el-dropdown-link" type="text">
                    更多<i class="el-icon-arrow-down el-icon--right"></i>
                  </el-button>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>编辑YAML</el-dropdown-item>
                    <el-dropdown-item>伸缩</el-dropdown-item>
                    <el-dropdown-item>回退</el-dropdown-item>
                    <el-dropdown-item>删除</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
            </el-row>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="pagesize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="deployments.length">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { deployApi } from '@/api/app/deploy'

export default {
  name: 'DeployList',
  data () {
    return {
      deployments: [],
      namespaceNames: ['default', 'kube-system', 'kubernetes-dashboard'],
      multipleSelection: [],
      currentPage: 1,
      pagesize: 10
    }
  },
  created () {
    /**
     * 创建完实例后获取一次数据
     */
    this.deployList()
  },
  mounted () {
    var _this = this
    this.timer = setInterval(function () {
      _this.deployList()
    }, 5000)
  },
  beforeDestroy () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  methods: {
    /**
     * 获取deployments数据
     */
    deployList () {
      deployApi.deployList({ namespaceNames: this.namespaceNames }).then(res => {
        this.deployments = res.data
      }).catch(err => {
        console.log(err)
      })
    },
    handleRestart (row) {
      var _this = this
      this.$http({
        method: 'put',
        url: '/app/deployment/restart',
        headers: {
          'Content-Type': 'application/json;charset=UTF-8'
        },
        data: {
          namespace: row.namespace,
          name: row.name
        }
      }).then(response => {
        if (response.status === 200) {
          if (response.data.code === '00000') {
            _this.$notify({
              title: '成功',
              message: '应用更新成功!',
              type: 'success'
            })
          } else {
            _this.$notify({
              title: response.data.code,
              message: response.data.description,
              type: 'error'
            })
          }
        }
      }).catch(error => {
        console.log('请求异常: ', error.message)
        this.$notify.error({
          title: '错误',
          message: '应用更新失败，请联系管理员!'
        })
      })
    },
    handleUpgrade (row) {
      this.$router.push({
        name: 'Deployment',
        params: {
          deployment: row,
          activeName: 'upgrade'
        }
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getRowKey (row) {
      return row.id
    },
    handleSizeChange: function (size) {
      this.pagesize = size
    },
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
    }
  }
}
</script>

<style scoped>
#deploy-list {
  height: 100%;
  padding: 0;
}
#header {
  height: 40px;
}
</style>
