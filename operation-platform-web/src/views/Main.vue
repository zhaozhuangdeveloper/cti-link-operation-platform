<template>
  <div id="main">
    <el-header height="50px">
    </el-header>
    <el-container>
      <el-aside width="180px" style="min-height: calc(100vh - 50px);">
        <el-menu
          default-active="1"
          class="el-menu-vertical-demo"
          unique-opened
          router>
          <el-submenu index="basic" disabled>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>基础服务管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home/basic/elasticsearch">ElasticSearch</el-menu-item>
              <el-menu-item index="/home/basic/logstash">Logstash</el-menu-item>
              <el-menu-item index="/home/basic/kibana">Kibana</el-menu-item>
              <el-menu-item index="/home/basic/redis">Redis</el-menu-item>
              <el-menu-item index="/home/basic/nginx">Nginx</el-menu-item>
              <el-menu-item index="/home/basic/postgresql">PostgreSQL</el-menu-item>
              <el-menu-item index="/home/basic/ceph">Ceph</el-menu-item>
              <el-menu-item index="/home/basic/consul">Consul</el-menu-item>
              <el-menu-item index="/home/basic/harbor">Harbor</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="resource" disabled>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>资源管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home/resource/ecs">服务器管理</el-menu-item>
              <el-menu-item index="/home/resource/cluster">集群管理</el-menu-item>
              <el-menu-item index="/home/resource/node">节点管理</el-menu-item>
              <el-menu-item index="/home/resource/network">网络管理</el-menu-item>
              <el-menu-item index="/home/resource/namespace">命名空间</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="configure" disabled>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>配置中心</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home/configure/configmap">配置项 ConfigMap</el-menu-item>
              <el-menu-item index="/home/configure/consul">配置项 Consul</el-menu-item>
              <el-menu-item index="/home/configure/secret">秘钥 Secret</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="application">
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>工作负载</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/app/deploy/list">无状态负载</el-menu-item>
              <el-menu-item index="/home/app/sts" disabled>有状态服务</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="gateway" disabled>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>网关管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home/gateway/servicegateway">业务网关</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
          <el-submenu index="log" disabled>
            <template slot="title">
              <i class="el-icon-menu"></i>
              <span>日志管理</span>
            </template>
            <el-menu-item-group>
              <el-menu-item index="/home/log/operation">操作日志</el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: 'Main'
}
</script>

<style scoped>
.el-header {
  background: #282b33;
}
.el-main{
  background: #eef0f5;
  padding: 0px;
}
</style>
