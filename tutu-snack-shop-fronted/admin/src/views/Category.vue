<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listCategories,
  addCategory,
  updateCategory,
  deleteCategory,
  type Category
} from '@/api/category'

const loading = ref(false)
const list = ref<Category[]>([])

const dialogVisible = ref(false)
const dialogTitle = ref('新增分类')
const editingId = ref<number | null>(null)
const form = reactive<Category>({ name: '' })

const loadData = async () => {
  loading.value = true
  try {
    list.value = await listCategories()
  } finally {
    loading.value = false
  }
}

const openAdd = () => {
  form.name = ''
  editingId.value = null
  dialogTitle.value = '新增分类'
  dialogVisible.value = true
}

const openEdit = (row: Category) => {
  form.name = row.name
  editingId.value = row.id!
  dialogTitle.value = '编辑分类'
  dialogVisible.value = true
}

const submit = async () => {
  if (!form.name) {
    ElMessage.warning('请输入分类名称')
    return
  }
  try {
    if (editingId.value) {
      await updateCategory({ id: editingId.value, name: form.name })
      ElMessage.success('修改成功')
    } else {
      await addCategory({ name: form.name })
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    await loadData()
  } catch (e) {
    // 错误已统一提示
  }
}

const remove = async (row: Category) => {
  try {
    await ElMessageBox.confirm(`确定删除分类「${row.name}」吗？`, '提示', { type: 'warning' })
    await deleteCategory(row.id!)
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
      <el-button type="primary" @click="openAdd">新增分类</el-button>
    </div>

    <el-table :data="list" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="90" />
      <el-table-column prop="name" label="分类名称" />
      <el-table-column label="操作" width="160" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="420px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="分类名称">
          <el-input v-model="form.name" placeholder="请输入分类名称" />
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
