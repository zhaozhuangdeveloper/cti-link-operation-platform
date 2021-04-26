<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}

/**修改全局的滚动条*/
/**滚动条的宽度*/
::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}
//滚动条的滑块
::-webkit-scrollbar-thumb {
  background-color: #a1a3a9;
  border-radius: 3px;
}
html,
body,
#app {
  margin: 0px;
  padding: 0px;
  height: 100%;
}
</style>
