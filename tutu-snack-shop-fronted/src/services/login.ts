import type { LoginResult } from "@/types/member"
import { http } from "@/utils/http"
type LoginParams = {
  code: string
  encryptedData: string
  iv: string
}
/**
 * 小程序登录
 * @param data 请求参数
 */
export const postLoginWxMinAPI = (data: LoginParams) => {
  return http<LoginResult>({
    method: 'POST',
    url: '/login/wxMin',
    data
  })
}
/**
 * 小程序登录_内测版(无需注册企业账号，个人开发者账号即可使用)
 * @param phoneNumber 模拟手机号码
 */
export const postLoginWxMinSimpleAPI = (phoneNumber: string) => {
  return http<LoginResult>({
    method: 'POST',
    url: '/login/wxMin/simple',
    data: { phoneNumber }
  })
}