/**
 * 请求拦截器
 * 
 * 添加拦截器：
 *  拦截request请求；拦截uploadFile文件上传
 */
import { useMemberStore } from "@/stores"
const baseUrl = 'http://localhost:8080'

//添加拦截器
const httpInterceptor = {
  //拦截前触发
  invoke(options:UniApp.RequestOptions){
    // 1. 非 http 开头需拼接地址
    if(!options.url.startsWith('http')){
      options.url = baseUrl + options.url
    }
    // 2. 请求超时
    options.timeout = 10000
    // 3. 添加小程序端请求头标识
    options.header = {
      'source-client': 'miniapp',
      //...options.header：使用展开运算符，将原有请求头对象的所有属性复制到新对象中
      ...options.header
    }
    // 4. 添加 token 请求头标识
    const memberStore = useMemberStore()
    //如果不使用问号，当 memberStore.profile 为 null 或 undefined 时，直接访问 memberStore.profile.token 会导致运行时错误
    const token = memberStore.profile?.token
    if(token){
      options.header.Authorization = token
    }
  }
}
// 拦截 request 请求
uni.addInterceptor('request',httpInterceptor)
// 拦截 uploadFile 文件上传
uni.addInterceptor('uploadFile',httpInterceptor)

/**
 * 响应拦截器
 * 
 * 请求函数
 * @param  UniApp.RequestOptions
 * @returns Promise
 *  1. 返回 Promise 对象，用于处理返回值类型
 *  2. 获取数据成功
 *    2.1 提取核心数据 res.data
 *    2.2 添加类型，支持泛型
 *  3. 获取数据失败
 *    3.1 401错误  -> 清理用户信息，跳转到登录页
 *    3.2 其他错误 -> 根据后端错误信息轻提示
 *    3.3 网络错误 -> 提示用户换网络
 */
interface Data<T>{ 
  code:number
  message:string
  data:T
}
export const http = <T>(options: UniApp.RequestOptions)=>{
  //1.返回 Promise 对象
  return new Promise<Data<T>>((resolve,reject)=>{
    uni.request({
      ...options,
      // 2.请求成功，响应数据
      success(res){
        //状态码2xx，axios就是这样设计的
        if(res.statusCode >= 200 && res.statusCode < 300){
          // 2.1 提取核心数据 res.data
          //as Data<T> 是 TypeScript 中的类型断言语法，表示将 res.data 强制认定为 Data<T> 类型
          resolve(res.data as Data<T>)
        }else if(res.statusCode === 401){
          //401错误  -> 清理用户信息，跳转到登录页
          const memberStore = useMemberStore()
          memberStore.clearProfile()
          //跳转
          uni.navigateTo({ url: '/pages/login/login' })
          reject(res)
        }else{
          //其他错误 -> 根据后端错误信息轻提示
          uni.showToast({
            icon: 'none',
            //先把res.data 强制认定为 Data<T> 类型再获取里面的message属性
            title: (res.data as Data<T>).message || '请求错误'
          })
          reject(res)
        }
      },
      //响应失败
      fail(err){
        uni.showToast({
          icon: 'none',
          title: '网络错误，换个网络试试'
        })
        reject(err)
      }
    })
  })
}