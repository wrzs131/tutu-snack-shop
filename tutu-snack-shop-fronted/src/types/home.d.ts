/** 首页-广告区域数据类型 */
// export type BannerItem = {
//   /** 跳转链接 */
//   hrefUrl: string
//   /** id */
//   id: string
//   /** 图片链接 */
//   imgUrl: string
//   /** 跳转类型 */
//   type: number
// }
// /** 首页-前台类目数据类型 */
// export type CategoryItem = {
//   /** 图标路径 */
//   icon: string
//   /** id */
//   id: string
//   /** 分类名称 */
//   name: string
// }

/** 分类项数据类型 */
export type CategoryDataItem = {
  /** id */
  id: number
  /** 分类名称 */
  name: string
  /** 创建时间 */
  createdAt: string
  /** 更新时间 */
  updatedAt: string
}

/** 分类列表响应数据类型 */
export type CategoryListResponse = {
  /** 状态码 */
  code: number
  /** 消息 */
  message: string
  /** 数据 */
  data: CategoryDataItem[]
}

/** 商品项数据类型 */
export type ProductItem = {
  /** id */
  id: number
  /** 商品名称 */
  name: string
  /** 销售价格 */
  price: number
  /** 原价 */
  originalPrice: number
  /** 库存数量 */
  stock: number
  /** 商品图片路径 */
  image: string
  /** 所属分类ID */
  categoryId: number
  /** 折扣信息 */
  discount: string
  /** 创建时间 */
  createdAt: string
  /** 更新时间 */
  updatedAt: string
}

/** 商品列表响应数据类型 */
export type ProductListResponse = {
  /** 状态码 */
  code: number
  /** 消息 */
  message: string
  /** 数据 */
  data: ProductItem[]
}

/** 店铺信息数据类型 */
export type ShopInfo = {
  /** id */
  id: number
  /** 店铺名称 */
  name: string
  /** 店铺logo */
  logo: string
  /** 店铺状态 */
  status: string
  /** 营业时间 */
  businessHours: string
  /** 联系电话 */
  phone: string
  /** 店铺地址 */
  address: string
  /** 创建时间 */
  createdAt: string
  /** 更新时间 */
  updatedAt: string
}

/** 店铺信息响应数据类型 */
export type ShopListResponse = {
  /** 状态码 */
  code: number
  /** 消息 */
  message: string
  /** 数据 */
  data: ShopInfo[]
}