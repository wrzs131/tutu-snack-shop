/**
 * 后端服务地址统一配置
 *
 * 不同运行环境的 localhost 含义不同：
 * - 微信开发者工具 / H5：localhost 指向电脑本机，可直接访问 http://localhost:8080
 * - App（MuMu 等安卓模拟器）：localhost 指向模拟器内部的安卓系统，
 *   访问不到电脑上的服务；MuMu 的 NAT 网络将电脑主机映射为 10.0.2.2，
 *   所以 App 端改用 10.0.2.2
 * - App（真机调试）：把下面 APP 端的 HOST 改成电脑的局域网 IP，
 *   例如 192.168.3.8（手机需与电脑处于同一 Wi-Fi）
 */
let HOST = 'localhost'

// #ifdef APP-PLUS
HOST = '10.0.2.2'
// #endif

/** 图图零食店业务后端（Spring Boot，8080） */
export const baseUrl = `http://${HOST}:8080`

/** 账号模拟服务（登录 / 注册，8000） */
export const mockBaseUrl = `http://${HOST}:8000`
