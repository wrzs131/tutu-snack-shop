<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  listOrders,
  listOrderItems,
  flowOrderStatus,
  type Order,
  type OrderItem
} from '@/api/order'

const role = localStorage.getItem('admin_role') || 'admin'

const loading = ref(false)
const list = ref<Order[]>([])
const total = ref(0)
const page = ref(1)
const pageSize = ref(10)
const statusFilter = ref('')

const statusOptions = ['待确认', '已接单', '已取餐', '配送中', '已送达', '已退款', '已完成', '已取消']

// 详情弹框
const detailVisible = ref(false)
const detailOrder = ref<Order | null>(null)
const detailItems = ref<OrderItem[]>([])

const loadOrders = async () => {
  loading.value = true
  try {
    const res = await listOrders({
      page: page.value,
      pageSize: pageSize.value,
      status: statusFilter.value || undefined
    })
    list.value = res.rows
    total.value = res.total
  } finally {
    loading.value = false
  }
}

const onPageChange = (p: number) => {
  page.value = p
  loadOrders()
}

const onFilterChange = () => {
  page.value = 1
  loadOrders()
}

// 状态标签颜色
const statusTagType = (status: string) => {
  switch (status) {
    case '待确认':
      return 'warning'
    case '已接单':
      return 'primary'
    case '已取餐':
      return 'info'
    case '配送中':
      return 'primary'
    case '已送达':
      return 'success'
    case '已完成':
      return 'success'
    case '已退款':
      return 'danger'
    case '已取消':
      return 'danger'
    default:
      return 'info'
  }
}

interface FlowAction {
  label: string
  target: string
  type: 'primary' | 'success' | 'danger' | 'warning'
}

// 根据角色 + 当前状态，计算可执行的状态操作
const availableActions = (order: Order): FlowAction[] => {
  const acts: FlowAction[] = []
  const s = order.status
  const canMerchant = role === 'merchant' || role === 'admin'
  const canRider = role === 'rider' || role === 'admin'
  if (s === '待确认') {
    if (canMerchant) acts.push({ label: '商家接单', target: '已接单', type: 'primary' })
    if (role === 'admin') acts.push({ label: '退款', target: '已退款', type: 'danger' })
  } else if (s === '已接单') {
    if (canRider) acts.push({ label: '确认取餐', target: '已取餐', type: 'primary' })
  } else if (s === '已取餐') {
    if (canRider) acts.push({ label: '开始配送', target: '配送中', type: 'primary' })
  } else if (s === '配送中') {
    if (canRider) acts.push({ label: '确认送达', target: '已送达', type: 'success' })
  }
  return acts
}

const doFlow = async (order: Order, target: string) => {
  try {
    await ElMessageBox.confirm(`确认将订单 #${order.id} 状态变更为「${target}」？`, '提示', {
      type: 'warning'
    })
    await flowOrderStatus(order.id, target, role)
    ElMessage.success('操作成功')
    await loadOrders()
  } catch (e) {
    // 取消或错误
  }
}

const openDetail = async (order: Order) => {
  detailOrder.value = order
  detailVisible.value = true
  try {
    detailItems.value = await listOrderItems(order.id)
  } catch (e) {
    detailItems.value = []
  }
}

onMounted(loadOrders)
</script>

<template>
  <div class="page">
    <div class="toolbar">
      <span class="label">状态筛选：</span>
      <el-select v-model="statusFilter" placeholder="全部" clearable style="width: 160px" @change="onFilterChange">
        <el-option v-for="s in statusOptions" :key="s" :label="s" :value="s" />
      </el-select>
      <el-button @click="loadOrders">刷新</el-button>
    </div>

    <el-table :data="list" v-loading="loading" border stripe>
      <el-table-column prop="id" label="订单ID" width="90" />
      <el-table-column prop="userId" label="用户ID" width="90" />
      <el-table-column prop="totalAmount" label="金额(¥)" width="110" />
      <el-table-column label="状态" width="110">
        <template #default="{ row }">
          <el-tag :type="statusTagType(row.status)">{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createdAt" label="下单时间" min-width="170" />
      <el-table-column label="操作" width="240" fixed="right">
        <template #default="{ row }">
          <el-button link type="info" @click="openDetail(row)">详情</el-button>
          <el-button
            v-for="act in availableActions(row)"
            :key="act.target"
            link
            :type="act.type"
            @click="doFlow(row, act.target)"
          >
            {{ act.label }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="pager">
      <el-pagination
        layout="total, prev, pager, next"
        :total="total"
        :page-size="pageSize"
        :current-page="page"
        @current-change="onPageChange"
      />
    </div>

    <el-dialog v-model="detailVisible" title="订单详情" width="560px">
      <div v-if="detailOrder" class="detail-head">
        <p>订单编号：{{ detailOrder.id }}</p>
        <p>用户ID：{{ detailOrder.userId }}</p>
        <p>总金额：¥{{ detailOrder.totalAmount }}</p>
        <p>状态：<el-tag :type="statusTagType(detailOrder.status)">{{ detailOrder.status }}</el-tag></p>
        <p>下单时间：{{ detailOrder.createdAt }}</p>
      </div>
      <el-divider>商品明细</el-divider>
      <el-table :data="detailItems" border size="small">
        <el-table-column label="商品" min-width="140">
          <template #default="{ row }">
            {{ row.product?.name || ('商品#' + row.productId) }}
          </template>
        </el-table-column>
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="price" label="单价(¥)" width="100" />
        <el-table-column label="小计(¥)" width="110">
          <template #default="{ row }">{{ (row.price * row.quantity).toFixed(2) }}</template>
        </el-table-column>
      </el-table>
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
  align-items: center;
  gap: 12px;
  margin-bottom: 16px;
}
.label {
  color: #606266;
}
.pager {
  margin-top: 16px;
  display: flex;
  justify-content: flex-end;
}
.detail-head p {
  margin: 6px 0;
  color: #303133;
}
</style>
