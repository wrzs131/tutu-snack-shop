<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listUsers,
  addUser,
  updateUser,
  deleteUser,
  type User
} from '@/api/user'

const loading = ref(false)
const list = ref<User[]>([])

const dialogVisible = ref(false)
const dialogTitle = ref('新增用户')
const editingId = ref<number | null>(null)
const form = reactive<User>({
  nickname: '',
  phone: '',
  role: 'customer',
  username: '',
  password: '',
  status: 'active'
})

const roleOptions = [
  { value: 'admin', label: '管理员' },
  { value: 'merchant', label: '商家' },
  { value: 'rider', label: '骑手' },
  { value: 'customer', label: '普通用户' }
]

const roleLabel = (role: string) =>
  roleOptions.find((r) => r.value === role)?.label || role

const roleTagType = (role: string) => {
  switch (role) {
    case 'admin':
      return 'danger'
    case 'merchant':
      return 'warning'
    case 'rider':
      return 'success'
    default:
      return 'info'
  }
}

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listUsers()
  } finally {
    loading.value = false
  }
}

const resetForm = () => {
  form.nickname = ''
  form.phone = ''
  form.role = 'customer'
  form.username = ''
  form.password = ''
  form.status = 'active'
  editingId.value = null
}

const openAdd = () => {
  resetForm()
  dialogTitle.value = '新增用户'
  dialogVisible.value = true
}

const openEdit = (row: User) => {
  resetForm()
  editingId.value = row.id!
  form.nickname = row.nickname
  form.phone = row.phone || ''
  form.role = row.role
  form.username = row.username || ''
  form.password = row.password || ''
  form.status = row.status || 'active'
  dialogTitle.value = '编辑用户'
  dialogVisible.value = true
}

const submit = async () => {
  if (!form.nickname) {
    ElMessage.warning('请输入昵称')
    return
  }
  try {
    if (editingId.value) {
      // 编辑时账号/密码留空则不提交该字段，后端 IFNULL 保持原值
      await updateUser({
        ...form,
        id: editingId.value,
        username: form.username || undefined,
        password: form.password || undefined
      })
      ElMessage.success('修改成功')
    } else {
      await addUser({ ...form })
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    await loadData()
  } catch (e) {
    // 错误已统一提示
  }
}

const remove = async (row: User) => {
  try {
    await ElMessageBox.confirm(`确定删除用户「${row.nickname}」吗？`, '提示', { type: 'warning' })
    await deleteUser(row.id!)
    ElMessage.success('删除成功')
    await loadData()
  } catch (e) {
    // 取消或错误
  }
}

onMounted(loadData)
</script>

<template>
  <div class="page">
    <div class="toolbar">
      <el-button type="primary" @click="openAdd">新增用户</el-button>
    </div>

    <el-table :data="list" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column prop="nickname" label="昵称" min-width="120" />
      <el-table-column prop="phone" label="手机号" width="140" />
      <el-table-column label="角色" width="110">
        <template #default="{ row }">
          <el-tag :type="roleTagType(row.role)">{{ roleLabel(row.role) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="登录账号" width="120" />
      <el-table-column label="状态" width="100">
        <template #default="{ row }">
          <el-tag :type="row.status === 'disabled' ? 'info' : 'success'">
            {{ row.status === 'disabled' ? '禁用' : '启用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="注册时间" min-width="170" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="昵称">
          <el-input v-model="form.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" placeholder="可留空" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.role" style="width: 100%">
            <el-option v-for="r in roleOptions" :key="r.value" :label="r.label" :value="r.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="登录账号">
          <el-input v-model="form.username" placeholder="员工账号，普通用户可留空" />
        </el-form-item>
        <el-form-item label="登录密码">
          <el-input v-model="form.password" placeholder="员工密码，普通用户可留空" show-password />
        </el-form-item>
        <el-form-item label="账号状态">
          <el-radio-group v-model="form.status">
            <el-radio value="active">启用</el-radio>
            <el-radio value="disabled">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.page {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
}
.toolbar {
  margin-bottom: 16px;
}
</style>
