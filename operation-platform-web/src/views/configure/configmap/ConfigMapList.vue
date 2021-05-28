<template>
  <div id="configMap-list">
    <div id="header">
      <div style="float: right">
        <el-button size="mini" class="button">创建ConfigMap</el-button>
      </div>
    </div>
    <div id="table" style="border: 1px solid #FFFFFF; padding: 10px; background: #FFFFFF; min-height: calc(100vh - 200px);">
      <el-button :disabled="multipleSelection.length === 0" size="mini">删除ConfigMap</el-button>
      <div style="float:right; margin-right: 10px;">
        <el-select
          v-model="namespaces"
          multiple
          collapse-tags
          placeholder="选择命名空间"
          size="mini"
          style="margin-right: 10px;">
          <el-option
            v-for="item in namespaceOption"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
        <el-input
          style="width: 200px; margin-right: 10px;"
          placeholder="ConfigMap名称搜索"
          suffix-icon="el-icon-search"
          v-model="name"
          clearable
          size="mini">
        </el-input>
      </div>
      <el-table
        v-loading="loading"
        element-loading-text="正在加载数据..."
        element-loading-spinner="el-icon-loading"
        :header-cell-style="{
            'background-color': '#f2f5fc'
        }"
        ref="multipleTable"
        :data="data.slice((currentPage-1)*pagesize,currentPage*pagesize)"
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
          label="ConfigMap名称">
          <template slot-scope="scope">
            <el-link type="primary" @click="editYaml(scope.row)">
              {{ scope.row.name }}
            </el-link>
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
            <el-button @click="handleRestart(scope.row)" type="text" size="mini">删除</el-button>
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
        :total="data.length">
      </el-pagination>
    </div>
    <el-dialog
      title="编辑YAML"
      :visible.sync="yamlVisible"
      width="40%" center>
      <yaml-editor v-model="configMap.yaml"/>
      <span slot="footer" class="dialog-footer">
          <a class="el-button el-button--mini" style="color: #606266;" >修 改</a>
          <a class="el-button el-button--mini" style="text-decoration:none; color: #606266;" :href="href()"
             :download="download()">下 载</a>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { configMapAPI } from '@/api/configure/configmap'
import { namespaceApi } from '@/api/app/namespace'
import { getDate } from '@/lib/util'
import YamlEditor from '@/components/module/app/deploy/YamlEditor'
export default {
  name: 'ConfigMapList',
  components: {
    YamlEditor
  },
  data () {
    return {
      loading: true,
      name: '',
      namespaceOption: [],
      configMaps: [],
      namespaces: ['default'],
      multipleSelection: [],
      currentPage: 1,
      pagesize: 10,
      yamlVisible: false,
      configMap: {}
    }
  },
  created () {
    this.namespaceList()
    this.configMapList()
  },
  computed: {
    data: function () {
      return this.configMaps.filter(item => {
        return this.namespaces.indexOf(item.namespace) !== -1
      }).filter(item => {
        return item.name.indexOf(this.name) !== -1
      })
    }
  },
  methods: {
    formatDate (row, column) {
      return getDate(row.creationTimestamp)
    },
    namespaceList () {
      namespaceApi.namespaceList().then(res => {
        this.namespaceOption = res.data
        this.loading = false
      }).catch(err => {
        console.log(err)
      })
    },
    configMapList () {
      configMapAPI.configMapList().then(res => {
        this.configMaps = res.data
      }).catch(err => {
        console.log(err)
      })
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
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    editYaml (row) {
      this.configMap = row
      this.yamlVisible = true
    },
    href () {
      var blob = new Blob([this.configMap.yaml], { type: 'text/plain' })
      var url = window.URL.createObjectURL(blob)
      return url
    },
    download () {
      return this.configMap.name + '.yaml'
    }
  }
}
</script>

<style scoped>
#configMap-list {
  height: 100%;
  padding: 10px;
}

#header {
  height: 40px;
}

.button {
  background: #c7000b;
  color: #FFF
}
</style>
