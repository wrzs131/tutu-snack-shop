import type { CategoryDataItem, ProductItem, ShopInfo } from "@/types/home.d"
import { http } from "@/utils/http"

/**
 * 首页-广告区域-小程序
 * @param distributionSite 广告区域展示位置（投放位置 投放位置，1为首页，2为分类商品页） 默认是1
 */
// export const getHomeBannerAPI = (distributionSite = 1) => {
//   return http<any>({
//     method: 'GET',
//     url: '/home/banner',
//     data: {
//       distributionSite
//     }
//   })
// }

/**
 * 获取分类列表
 */
export const getCategoryListAPI = () => {
  return http<CategoryDataItem[]>({
    method: 'GET',
    url: '/category/list'
  })
}

/**
 * 获取分类商品列表
 * @param categoryId 分类ID
 */
export const getCategoryProductsAPI = (categoryId: number) => {
  return http<ProductItem[]>({
    method: 'GET',
    url: `/product/listByCategoryId/${categoryId}`
  })
}

/**
 * 获取商品列表
 */
export const getProductListAPI = () => {
  return http<ProductItem[]>({
    method: 'GET',
    url: '/product/list'
  })
}

/**
 * 获取店铺信息列表
 */
export const getShopListAPI = () => {
  return http<ShopInfo[]>({
    method: 'GET',
    url: '/shop/list'
  })
} 