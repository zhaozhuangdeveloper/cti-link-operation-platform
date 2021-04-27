<template>
  <div id="pod-list">
    <el-button type="danger" :disabled="multipleSelection.length ===0 ? true: false" size="mini">删除实例</el-button>
    <el-table
      :data="pods"
      style="width: 100%"
      :row-key="getKey"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55"
        reserve-selection>
      </el-table-column>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table :data="props.row.containers">
            <el-table-column
              align="center"
              prop="name"
              label="容器名称">
            </el-table-column>
            <el-table-column
              align="center"
              prop="status"
              label="状态">
            </el-table-column>
            <el-table-column
              align="center"
              prop="image"
              label="镜像">
            </el-table-column>
            <el-table-column
              align="center"
              prop="restartCount"
              label="重启次数">
            </el-table-column>
            <el-table-column
              align="center"
              label="操作">
              <template slot-scope="scope">
                <el-button @click="handleConsole(props.row.name, scope.row)" type="text">控制台</el-button>
                <el-button @click="handleLog(props.row.name, scope.row)" type="text">控制台日志</el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column
        prop="name"
        label="实例(Pod)"
        show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        align="center"
        prop="status"
        label="状态">
      </el-table-column>
      <el-table-column
        align="center"
        label="容器个数(正常/全部)">
        <template slot-scope="scope">{{ scope.row.readyContainers }}/{{ scope.row.totalContainers }}</template>
      </el-table-column>
      <el-table-column
        align="center"
        prop="hostIP"
        label="所在节点">
      </el-table-column>
      <el-table-column
        align="center"
        prop="podIP"
        label="实例IP">
      </el-table-column>
      <el-table-column
        align="center"
        prop="restartCount"
        label="重启次数">
      </el-table-column>
      <el-table-column
        align="center"
        prop="creationTimestamp"
        :formatter="formatDate"
        label="创建时间">
      </el-table-column>
      <el-table-column
        align="center"
        label="操作">
        <template slot-scope="scope">
          <el-button @click="handleDelete(scope.row)" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { podApi } from '@/api/app/pod'
import { getDate } from '@/lib/util'

export default {
  name: 'PodList',
  data () {
    return {
      pods: [],
      multipleSelection: []
    }
  },
  props: {
    deploy: {
      type: Object
    }
  },
  created () {
    this.podList()
  },
  mounted () {
    var _this = this
    this.timer = setInterval(function () {
      _this.podList()
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
    podList () {
      podApi.podList(this.deploy).then(res => {
        this.pods = res.data
      })
    },
    handleSelectionChange (val) {
      this.multipleSelection = val
    },
    getKey (row) {
      return row.id
    }
  }
}
</script>

<style scoped lang="scss">

</style>
