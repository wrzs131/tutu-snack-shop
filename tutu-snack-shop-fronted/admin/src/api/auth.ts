import request from '@/utils/request'

export interface AdminLoginResult {
  id: number
  username: string
  nickname: string
  role: string
  token: string
}

/** 后台账号登录 */
export const adminLogin = (data: {
  username: string
  password: string
}) => {
  return request.post('/admin/login', data) as unknown as Promise<AdminLoginResult>
}
