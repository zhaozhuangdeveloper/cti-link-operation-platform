<template>
  <div id="deploy-upgrade">
    <div id="containers">
      <div :key="index" v-for="(container, index) in deployUpgrade.containers" class="container-item"
           :class="{active:activeIndex == index}" @click="active(index)">
        {{ container.name }}
      </div>
    </div>
    <div id="container-content" style="clear: left;">
      <div :key="index" v-for="(container, index) in deployUpgrade.containers" class="container-content"
           v-show="activeIndex == index">
        <a class="label">当前版本: </a>
        <strong> {{ container.image }}</strong>
        <br/><br/>
        <a class="label">更新版本: </a>
        <el-select v-model="container.image"
                   placeholder="请选择" size="mini">
          <el-option
            v-for="tag in container.tags"
            :key="tag.value"
            :label="tag.label"
            :value="tag.value">
          </el-option>
        </el-select>
      </div>
      <div id="submit">
        <el-button @click="upgrade" style="float: right; width: 150px;" class="button">提交</el-button>
      </div>
    </div>
  </div>
</template>

<script>

import { deployApi } from '@/api/app/deploy'

export default {
  name: 'DeployUpgrade',
  data () {
    return {
      activeIndex: 0,
      deployUpgrade: {}
    }
  },
  created () {
    this.getDeployupgrade(this.deploy)
  },
  props: {
    deploy: {
      type: Object
    }
  },
  methods: {
    active (index) {
      this.activeIndex = index
    },
    upgrade () {
      var _this = this
      var data = {
        name: _this.deployUpgrade.name,
        namespace: _this.deployUpgrade.namespace,
        containers: []
      }
      for (var container of _this.deployUpgrade.containers) {
        var param = {
          name: container.name,
          image: container.image
        }
        data.containers.push(param)
      }
      deployApi.deployUpgrade(data).then(res => {
        _this.$notify({
          title: '成功',
          message: '应用升级成功!',
          type: 'success'
        })
      }).catch(err => {
        console.log(err)
      })
    },
    getDeployupgrade (deploy) {
      deployApi.getDeployUpgrade(deploy).then(res => {
        this.deployUpgrade = res.data
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped lang="scss">
#containers {
  padding: 20px;
}

.container-item {
  width: 173px;
  height: 38px;
  padding: 20px;
  border: #cccccc solid 1px;
  border-radius: 4px;
  text-align: center;
  line-height: 38px;
  float: left;
  margin-left: 20px;
}

.active {
  border: #526ecc solid 1px;
}

.container-content {
  border: #bac5eb solid 2px;
  margin-top: 80px;
  height: 100px;
  border-radius: 4px;
  padding: 20px;
}

#submit {
  margin-top: 20px;
}

.label {
  color: #888;
}

.button {
  background: #c7000b;
  color: #FFF
}
</style>
