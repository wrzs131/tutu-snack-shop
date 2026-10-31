<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listProducts,
  addProduct,
  updateProduct,
  deleteProduct,
  type Product
} from '@/api/product'
import { listCategories, type Category } from '@/api/category'
import { BASE_URL } from '@/utils/request'

const loading = ref(false)
const list = ref<Product[]>([])
const categories = ref<Category[]>([])
const keyword = ref('')

const dialogVisible = ref(false)
const dialogTitle = ref('新增商品')
const editingId = ref<number | null>(null)
const form = reactive<Product>({
  name: '',
  price: 0,
  originalPrice: 0,
  stock: 0,
  image: '/static/mian.png',
  categoryId: 1,
  discount: ''
})

const imageUrl = (image: string) =>
  !image ? '' : image.startsWith('http') ? image : BASE_URL + image

const categoryName = (id: number) =>
  categories.value.find((c) => c.id === id)?.name || `分类${id}`

const loadData = async () => {
  loading.value = true
  try {
    const [products, cats] = await Promise.all([listProducts(), listCategories()])
    list.value = products
    categories.value = cats
  } finally {
    loading.value = false
  }
}

const filteredList = () =>
  keyword.value
    ? list.value.filter((p) => p.name.includes(keyword.value))
    : list.value

const resetForm = () => {
  form.name = ''
  form.price = 0
  form.originalPrice = 0
  form.stock = 0
  form.image = '/static/mian.png'
  form.categoryId = categories.value[0]?.id || 1
  form.discount = ''
  editingId.value = null
}

const openAdd = () => {
  resetForm()
  dialogTitle.value = '新增商品'
  dialogVisible.value = true
}

const openEdit = (row: Product) => {
  resetForm()
  editingId.value = row.id!
  Object.assign(form, row)
  dialogTitle.value = '编辑商品'
  dialogVisible.value = true
}

const submit = async () => {
  if (!form.name) {
    ElMessage.warning('请输入商品名称')
    return
  }
  try {
    if (editingId.value) {
      await updateProduct({ ...form, id: editingId.value })
      ElMessage.success('修改成功')
    } else {
      await addProduct({ ...form })
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
    await loadData()
  } catch (e) {
    // 错误已统一提示
  }
}

const remove = async (row: Product) => {
  try {
    await ElMessageBox.confirm(`确定删除商品「${row.name}」吗？`, '提示', { type: 'warning' })
    await deleteProduct(row.id!)
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
      <el-input v-model="keyword" placeholder="按商品名称搜索" clearable style="width: 240px" />
      <el-button type="primary" @click="openAdd">新增商品</el-button>
    </div>

    <el-table :data="filteredList()" v-loading="loading" border stripe>
      <el-table-column prop="id" label="ID" width="70" />
      <el-table-column label="图片" width="90">
        <template #default="{ row }">
          <el-image
            :src="imageUrl(row.image)"
            :preview-src-list="[imageUrl(row.image)]"
            fit="cover"
            style="width: 48px; height: 48px; border-radius: 6px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品名称" min-width="140" />
      <el-table-column prop="price" label="售价(¥)" width="100" />
      <el-table-column prop="originalPrice" label="原价(¥)" width="100" />
      <el-table-column prop="stock" label="库存" width="90" />
      <el-table-column label="分类" width="120">
        <template #default="{ row }">{{ categoryName(row.categoryId) }}</template>
      </el-table-column>
      <el-table-column prop="discount" label="折扣" width="100" />
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="openEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="商品名称">
          <el-input v-model="form.name" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="售价">
          <el-input-number v-model="form.price" :min="0" :precision="2" :step="0.5" />
        </el-form-item>
        <el-form-item label="原价">
          <el-input-number v-model="form.originalPrice" :min="0" :precision="2" :step="0.5" />
        </el-form-item>
        <el-form-item label="库存数量">
          <el-input-number v-model="form.stock" :min="0" :step="1" />
        </el-form-item>
        <el-form-item label="所属分类">
          <el-select v-model="form.categoryId" placeholder="请选择分类" style="width: 100%">
            <el-option
              v-for="c in categories"
              :key="c.id"
              :label="c.name"
              :value="c.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="图片路径">
          <el-input v-model="form.image" placeholder="如 /static/mian.png" />
        </el-form-item>
        <el-form-item label="折扣">
          <el-input v-model="form.discount" placeholder="如 9.0折，可留空" />
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
  display: flex;
  gap: 12px;
  margin-bottom: 16px;
}
</style>
