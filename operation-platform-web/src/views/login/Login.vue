<template>
  <div id="login">
    <div id="form">
      <el-form status-icon :model="user" ref="user" :rules=rules>
        <el-form-item prop="userName">
          <el-input prefix-icon="el-icon-user" placeholder="请输入用户名" v-model.trim="user.userName"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="el-icon-lock" placeholder="请输入密码" type="password" show-password
                    v-model.trim="user.password" @keyup.enter.native="login"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
export default {
  name: 'Login',
  data () {
    return {
      user: {
        userName: '',
        password: ''
      },
      rules: {
        userName: [
          { required: true, message: '用户名不能为空', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '密码不能为空', triger: 'blur' }
        ]
      }
    }
  },
  methods: {
    ...mapActions(['handleLogin']),
    login () {
      this.$refs.user.validate(valid => {
        if (valid) {
          this.handleLogin(this.user).then(res => {
            this.$router.push({
              name: 'Main'
            })
          }).catch(err => {
            console.log(err)
          })
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style scoped>
#form {
  width: 20%;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
#login{
  width: 100%;
  height: 100%;
  background-image: url(../../assets/images/login-bg.jpg);
}
</style>
