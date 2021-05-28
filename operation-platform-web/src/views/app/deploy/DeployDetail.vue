<template>
  <div id="deploy-detail">
    <div style="height: 40px; background: #FFF; padding-top: 10px">
      <el-page-header @back="goBack" :content="deploy.name">
      </el-page-header>
      <el-button style="position: absolute; top: 60px; right: 10px" size="mini"
                 @click="editYaml" class="button">编辑YAML文件
      </el-button>
    </div>
    <div style="padding: 10px">
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
          <a class="el-button el-button--mini" style="color: #606266;" @click="deployUpgradeYaml">修 改</a>
          <a class="el-button el-button--mini" style="text-decoration:none; color: #606266;" :href="href()"
             :download="download()">下 载</a>
      </span>
      </el-dialog>
    </div>
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
    this.active = this.$store.state.app.active
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
    deployUpgradeYaml () {
      var _this = this
      deployApi.deployUpgradeYaml({
        yaml: _this.deploy.yaml
      }).then(res => {
        _this.yamlVisible = false
        _this.$notify({
          title: '成功',
          message: '应用升级成功!',
          type: 'success'
        })
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped lang="scss">

.button {
  background: #c7000b;
  color: #FFF
}

.text {
  font-size: 14px;
  text-align: left;
}

.item {
  padding: 18px 0;
}

.box-card {
  text-align: left;
}

.label {
  color: #888;
}

#nav {
  margin-top: 10px;
  padding: 10px;
  text-align: left;
  background: #FFFFFF;
  border-radius: 4px;
  min-height: 500px;
}
</style>
