<template>
  <div class="login-container">
    <el-form :model="loginForm" :rules="loginRules" ref="loginForm" class="login-form">
      <h3 class="title">登录</h3>

      <!-- 用户名 -->
      <el-form-item prop="username">
        <span class="svg-container">
          <i class="el-icon-user" style="font-size: 24px"></i>
        </span>
        <el-input
          class="yuan"
          v-model="loginForm.username"
          placeholder="请输入用户名"
          name="username"
          type="text"
          tabindex="1"
        />
      </el-form-item>

      <!-- 密码 -->
      <el-form-item prop="password">
        <span class="svg-container">
          <i class="el-icon-lock" style="font-size: 24px"></i>
        </span>
        <el-input
          class="yuan"
          v-model="loginForm.password"
          placeholder="请输入密码"
          name="password"
          type="password"
          tabindex="2"
          autocomplete="off"
        />
      </el-form-item>

      <!-- 验证码 -->
      <el-form-item prop="captcha">
        <div class="captcha-container">
          <el-input
            class="captcha-input"
            v-model="loginForm.captcha"
            placeholder="请输入验证码"
            name="captcha"
            type="text"
            tabindex="3"
          />
          <img
            :src="captchaSrc"
            alt="验证码"
            class="captcha-img"
            @click="refreshCaptcha"
          />
          <button type="button" class="refresh-btn" @click="refreshCaptcha">刷新验证码</button>
        </div>
      </el-form-item>

      <!-- 登录按钮 -->
      <el-form-item>
        <el-button
          type="primary"
          class="login-button"
          @click="handleLogin"
          :loading="loading"
        >
          登录
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loginForm: {
        username: "",
        password: "",
        captcha: "",
      },
      loginRules: {
        username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
        password: [{ required: true, message: "请输入密码", trigger: "blur" }],
        captcha: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
      captchaSrc: "/captcha", // 验证码路径
      loading: false,
    };
  },
  methods: {
    refreshCaptcha() {
      this.captchaSrc = `/captcha?${new Date().getTime()}`; // 防止缓存
    },
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          // 提交表单逻辑
        }
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: url("../../assets/图书馆.jpg") no-repeat center center;
  background-size: cover;
}

.login-form {
  width: 360px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.9);
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
  color: #333;
}

.svg-container {
  display: inline-block;
  margin-right: 8px;
  vertical-align: middle;
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-input {
  flex: 1;
}

.captcha-img {
  width: 120px;
  height: 40px;
  cursor: pointer;
  border: 1px solid #dcdfe6;
}

.refresh-btn {
  background: none;
  border: none;
  color: #409eff;
  cursor: pointer;
}

.login-button {
  width: 100%;
}
</style>
