<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Goods, Files, List, User } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()

const role = computed(() => localStorage.getItem('admin_role') || '')
const nickname = computed(() => localStorage.getItem('admin_nickname') || '管理员')

const ROLE_LABEL: Record<string, string> = {
  admin: '管理员',
  merchant: '商家',
  rider: '骑手'
}
const roleLabel = computed(() => ROLE_LABEL[role.value] || role.value)

const allMenus = [
  { path: '/products', title: '商品管理', icon: Goods, roles: ['admin', 'merchant'] },
  { path: '/categories', title: '分类管理', icon: Files, roles: ['admin', 'merchant'] },
  { path: '/orders', title: '订单管理', icon: List, roles: ['admin', 'merchant', 'rider'] },
  { path: '/users', title: '用户管理', icon: User, roles: ['admin'] }
]

const menus = computed(() => allMenus.filter((m) => m.roles.includes(role.value)))

const activeIndex = computed(() => route.path)
const currentTitle = computed(() => {
  const matched = allMenus.find((m) => m.path === route.path)
  return matched ? matched.title : '图图零食店后台'
})

const logout = () => {
  localStorage.removeItem('admin_token')
  localStorage.removeItem('admin_role')
  localStorage.removeItem('admin_username')
  localStorage.removeItem('admin_nickname')
  router.push('/login')
}
</script>

<template>
  <el-container class="layout">
    <el-aside width="220px" class="aside">
      <div class="logo">🍭 图图零食店</div>
      <el-menu :default-active="activeIndex" router class="menu" background-color="#1f2d3d"
        text-color="#cfd8e3" active-text-color="#ffd04b">
        <el-menu-item v-for="m in menus" :key="m.path" :index="m.path">
          <el-icon><component :is="m.icon" /></el-icon>
          <span>{{ m.title }}</span>
        </el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="header">
        <span class="title">{{ currentTitle }}</span>
        <div class="right">
          <el-tag :type="role === 'admin' ? 'danger' : role === 'merchant' ? 'warning' : 'success'">
            {{ roleLabel }}
          </el-tag>
          <span class="nick">{{ nickname }}</span>
          <el-button link type="danger" @click="logout">退出登录</el-button>
        </div>
      </el-header>
      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped>
.layout {
  height: 100vh;
}
.aside {
  background-color: #1f2d3d;
}
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 18px;
  font-weight: 600;
  background-color: #16222e;
}
.menu {
  border-right: none;
}
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background-color: #fff;
  border-bottom: 1px solid #ebeef5;
}
.title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}
.right {
  display: flex;
  align-items: center;
  gap: 12px;
}
.nick {
  color: #606266;
}
.main {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
