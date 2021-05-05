<template>
  <div id="deploy-detail">
    <el-page-header @back="goBack" :content="deploy.name">
    </el-page-header>
    <el-button style="width: 100px; position: absolute; top: 80px; right: 60px" type="danger" size="mini"
               @click="editYaml">编辑YAML
    </el-button>
    <el-divider></el-divider>
    <el-card class="box-card">
      <div class="text item">
        <el-row>
          <el-col :offset=1 :span=3 class="label">名称</el-col>
          <el-col :span=2>{{ deploy.name }}</el-col>
          <el-col :offset=6 :span=2 class="label">类型</el-col>
          <el-col :span=5>Deployment</el-col>
        </el-row>
      </div>
      <div class="text item">
        <el-row>
          <el-col :offset=1 :span=3 class="label">状态</el-col>
          <el-col :span=2>{{ deploy.status }}</el-col>
          <el-col :offset=6 :span=2 class="label">创建时间</el-col>
          <el-col :span=5>{{ deploy.creationTimestamp | getDate}}</el-col>
        </el-row>
      </div>
      <div class="text item">
        <el-row>
          <el-col :offset=1 :span=3 class="label">实例个数(正常/全部)</el-col>
          <el-col :span=2>{{ deploy.readyReplicas }}/{{ deploy.replicas }}</el-col>
          <el-col :offset=6 :span=2 class="label">命名空间</el-col>
          <el-col :span=5>{{ deploy.namespace }}</el-col>
        </el-row>
      </div>
      <div class="text item">
        <el-row>
          <el-col :offset=1 :span=3 class="label">升级方式</el-col>
          <el-col :span=2>{{ deploy.strategyType }}</el-col>
          <el-col :offset=6 :span=2 class="label">标签</el-col>
          <el-col :span=5>{{ deploy.labels }}</el-col>
        </el-row>
      </div>
    </el-card>
    <div id="nav">
      <el-tabs v-model="active" type="card">
        <el-tab-pane label="实例列表" name="podList" lazy>
          <PodList :deploy="this.$store.state.app.deploy"></PodList>
        </el-tab-pane>
        <el-tab-pane label="版本更新" name="deployUpgrade" lazy>
          <DeployUpgrade :deploy="this.$store.state.app.deploy"></DeployUpgrade>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-dialog
      title="编辑YAML"
      :visible.sync="yamlVisible"
      width="40%" center>
      <yaml-editor v-model="deploy.yaml"/>
      <span slot="footer" class="dialog-footer">
        <a class="el-button el-button--mini" style="color: #606266;" @click="upgradeYaml">修 改</a>
        <a class="el-button el-button--mini" style="text-decoration:none; color: #606266;" :href="href()"
           :download="download()">下 载</a>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import YamlEditor from '@/components/module/app/deploy/YamlEditor'
import PodList from '@/views/app/pod/PodList'
import { deployApi } from '@/api/app/deploy'
import { getDate } from '@/lib/util'
import DeployUpgrade from '@/views/app/deploy/DeployUpgrade'

export default {
  name: 'DeployDetail',
  components: {
    DeployUpgrade,
    YamlEditor,
    PodList
  },
  data () {
    return {
      deploy: {},
      active: 'podList',
      yamlVisible: false
    }
  },
  created () {
    this.deployDetail(this.$store.state.app.deploy)
  },
  filters: {
    getDate (timestamp) {
      return getDate(timestamp)
    }
  },
  methods: {
    goBack () {
      this.$router.back(-1)
    },
    deployDetail (deploy) {
      deployApi.deployDetail(deploy).then(res => {
        this.deploy = res.data
      }).catch(err => {
        console.log(err)
      })
    },
    editYaml () {
      this.yamlVisible = true
    },
    href () {
      var blob = new Blob([this.deploy.yaml], { type: 'text/plain' })
      var url = window.URL.createObjectURL(blob)
      return url
    },
    download () {
      return this.deploy.name + '.yaml'
    },
    upgradeYaml () {
    }
  }
}
</script>

<style scoped lang="scss">
.text {
  font-size: 14px;
  text-align: left;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 98%;
  text-align: left;
  margin-left: 1%;
}

.label {
  color: #888;
}

#nav {
  padding: 20px;
  margin-left: 1%;
  margin-right: 1%;
  text-align: left;
  margin-top: 20px;
  background: #FFFFFF;
  border-radius: 4px;
}
</style>
