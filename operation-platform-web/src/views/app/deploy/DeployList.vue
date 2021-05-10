<template>
  <div id="deploy-list">
    <div id="header">
      <div style="float: right">
        <el-button size="mini" class="button">创建无状态工作负载</el-button>
      </div>
    </div>
    <div id="table" style="border: 1px solid #FFFFFF; padding: 10px; background: #FFFFFF; min-height: calc(100vh - 200px);">
      <el-button :disabled="multipleSelection.length === 0" size="mini">删除工作负载</el-button>
      <el-button size="mini">选择命名空间</el-button>
      <el-button size="mini">全部状态</el-button>
      <el-button size="mini">搜索</el-button>
      <el-table
        :header-cell-style="{
            'background-color': '#f2f5fc'
        }"
        ref="multipleTable"
        :data="deploys.slice((currentPage-1)*pagesize,currentPage*pagesize)"
        tooltip-effect="dark"
        style="width: 100%; margin-top: 10px"
        :row-key="getRowKey"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"
          reserve-selection>
        </el-table-column>
        <el-table-column
          prop="name"
          label="工作负载名称">
          <template slot-scope="scope">
            <a style="color: #409EFF; cursor: pointer" @click="deployDetail(scope.row)">
              {{ scope.row.name }}
            </a>
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
          label="版本"
          min-width="250px">
          <template slot-scope="scope">
            <a v-for="(image, index) in scope.row.images" :key="index">{{ image }}<br /></a>
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          prop="namespace"
          label="命名空间">
        </el-table-column>
        <el-table-column
          align="center"
          prop="creationTimestamp"
          label="创建时间"
        :formatter="formatDate">
        </el-table-column>
        <el-table-column
          align="center"
          label="操作">
          <template slot-scope="scope">
            <el-row>
              <el-col :span="4" :offset="6">
                <el-button @click="handleRestart(scope.row)" type="text" size="mini">重启</el-button>
              </el-col>
              <el-col :span="4">
                <el-button @click="handleUpgrade(scope.row)" type="text" size="mini">升级</el-button>
              </el-col>
              <el-col :span="6">
                <el-dropdown trigger="click">
                  <el-button class="el-dropdown-link" type="text" size="mini">
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
        :total="deploys.length">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import { deployApi } from '@/api/app/deploy'
import { getDate } from '@/lib/util'

export default {
  name: 'DeployList',
  data () {
    return {
      deploys: [],
      namespaces: ['default'],
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
    formatDate (row, column) {
      return getDate(row.creationTimestamp)
    },
    /**
     * 获取deploys数据
     */
    deployList () {
      deployApi.deployList({ namespaces: this.namespaces }).then(res => {
        this.deploys = res.data
      }).catch(err => {
        console.log(err)
      })
    },
    handleRestart (row) {
      var _this = this
      deployApi.deployRestart({
        namespace: row.namespace,
        name: row.name
      }).then(res => {
        _this.$notify({
          title: '成功',
          message: '应用更新成功!',
          type: 'success'
        })
      }).catch(err => {
        console.log(err)
      })
    },
    handleUpgrade (row) {
      var deploy = {
        name: row.name,
        namespace: row.namespace
      }
      this.$store.commit('setDeploy', deploy)
      this.$store.commit('setActive', 'deployUpgrade')
      this.$router.push({
        name: 'DeployDetail'
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
    },
    deployDetail (row) {
      var data = {
        name: row.name,
        namespace: row.namespace
      }
      this.$store.commit('setActive', 'podList')
      this.$store.commit('setDeploy', data)
      this.$router.push({
        name: 'DeployDetail',
        params: data
      })
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
.button {
  background: #c7000b;
  color: #FFF
}

#deploy-list {
  padding: 10px;
}
</style>
