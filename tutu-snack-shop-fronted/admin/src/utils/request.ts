import axios from 'axios'
import { ElMessage } from 'element-plus'

// 后端基础地址
export const BASE_URL = 'http://localhost:8080'

const request = axios.create({
  baseURL: BASE_URL,
  timeout: 10000
})

// 请求拦截：携带登录令牌（后端读取的请求头名称为 authentication）
request.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('admin_token')
    if (token) {
      config.headers['authentication'] = token
    }
    return config
  },
  (error) => Promise.reject(error)
)

// 响应拦截：后端统一返回 { code, message, data }，code=1 表示成功
request.interceptors.response.use(
  (response) => {
    const body = response.data
    if (body && body.code === 1) {
      return body.data
    }
    ElMessage.error((body && body.message) || '请求失败')
    return Promise.reject(new Error((body && body.message) || '请求失败'))
  },
  (error) => {
    const msg =
      (error && error.response && error.response.data && error.response.data.message) ||
      (error && error.message) ||
      '网络错误'
    ElMessage.error(msg)
    return Promise.reject(error)
  }
)

export default request
