/** 订单查询参数类型 */
export type OrderQueryParam = {
  /** 页码 */
  page: number
  /** 每页大小 */
  pageSize: number
  /** 订单ID */
  // id: number | null
  /** 开始时间 */
  begin: string | null
  /** 结束时间 */
  end: string | null
}

/** 创建订单项参数 */
export type CreateOrderItemParam = {
  /** 商品ID */
  productId: number
  /** 数量 */
  quantity: number
  /** 价格 */
  price: number
}

/** 创建订单参数 */
export type CreateOrderParam = {
  /** 总金额 */
  totalAmount: number
  /** 订单项列表 */
  items: CreateOrderItemParam[]
}

/** 订单数据类型 */
export type OrderItem = {
  /** 订单ID */
  id: number
  /** 用户ID */
  userId: number
  /** 总金额 */
  totalAmount: number
  /** 订单状态 */
  status: string
  /** 创建时间 */
  createdAt: string
  /** 更新时间 */
  updatedAt: string
}

/** 订单列表数据类型 */
export type OrderListData = {
  /** 总记录数 */
  total: number
  /** 订单列表 */
  rows: OrderItem[]
}

/** 订单列表响应数据类型 */
export type OrderListResponse = OrderListData

/** 商品数据类型 */
export type Product = {
  /** 商品ID */
  id: number
  /** 商品名称 */
  name: string
  /** 商品价格 */
  price: number
  /** 原价 */
  originalPrice: number
  /** 库存 */
  stock: number
  /** 商品图片 */
  image: string
  /** 分类ID */
  categoryId: number
  /** 折扣 */
  discount: string
  /** 创建时间 */
  createdAt: string
  /** 更新时间 */
  updatedAt: string
}

/** 订单项数据类型 */
export type OrderItemDetail = {
  /** 订单项ID */
  id: number
  /** 订单ID */
  orderId: number
  /** 商品ID */
  productId: number
  /** 数量 */
  quantity: number
  /** 价格 */
  price: number
  /** 创建时间 */
  createdAt: string
  /** 商品信息 */
  product: Product
}

/** 订单项列表响应数据类型 */
export type OrderItemListResponse = OrderItemDetail[]