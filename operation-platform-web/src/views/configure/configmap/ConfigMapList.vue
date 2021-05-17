<template>
  <div id="config-map-list">
    {{ configMaps }}
  </div>
</template>

<script>
import { configMapAPI } from '@/api/configure/configmap'
export default {
  name: 'ConfigMapList',
  data () {
    return {
      configMaps: []
    }
  },
  created () {
  },
  methods: {
    configMapList () {
      configMapAPI.configMapList().then(res => {
        this.configMaps = res.data
      }).catch(err => {
        console.log(err)
      })
    }
  }
}
</script>

<style scoped>

</style>
