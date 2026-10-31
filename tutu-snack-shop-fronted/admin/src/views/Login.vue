<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { adminLogin } from '@/api/auth'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: '', password: '' })

const submit = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }
  loading.value = true
  try {
    const res = await adminLogin({ username: form.username, password: form.password })
    localStorage.setItem('admin_token', res.token)
    localStorage.setItem('admin_role', res.role)
    localStorage.setItem('admin_username', res.username)
    localStorage.setItem('admin_nickname', res.nickname)
    ElMessage.success('登录成功')
    router.push('/orders')
  } catch (e) {
    // 错误已在拦截器统一提示
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <div class="login-page">
    <div class="login-box">
      <div class="brand">🍭 图图零食店</div>
      <div class="sub">后台管理系统</div>
      <el-form :model="form" label-position="top" @keyup.enter="submit">
        <el-form-item label="账号">
          <el-input v-model="form.username" placeholder="请输入登录账号" :prefix-icon="'User'" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input
            v-model="form.password"
            type="password"
            placeholder="请输入密码"
            show-password
            :prefix-icon="'Lock'"
          />
        </el-form-item>
        <el-button type="primary" :loading="loading" class="login-btn" @click="submit">
          登 录
        </el-button>
      </el-form>
      <!-- <div class="tips">
        <div>演示账号（密码见括号）：</div>
        <div>管理员 admin（admin123） · 商家 merchant（merchant123） · 骑手 rider（rider123）</div>
      </div> -->
    </div>
  </div>
</template>

<style scoped>
.login-page {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}
.login-box {
  width: 380px;
  padding: 40px 36px;
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.2);
}
.brand {
  font-size: 26px;
  font-weight: 700;
  text-align: center;
  color: #333;
}
.sub {
  text-align: center;
  color: #909399;
  margin: 6px 0 28px;
}
.login-btn {
  width: 100%;
}
.tips {
  margin-top: 20px;
  font-size: 12px;
  color: #909399;
  line-height: 1.8;
  text-align: center;
}
</style>
