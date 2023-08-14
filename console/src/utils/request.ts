import axios from "axios";
import {ElLoading, ElMessage, ElMessageBox} from 'element-plus'

const request = axios.create({
  // baseURL: 'https://test-api.twhzx.com',
  baseURL: "/",
  timeout: 5000,
  headers: {
    "Content-Type": "application/json",
  }
})

request.interceptors.request.use((AxiosConfig) => {
    console.log('请求拦截器');
    return AxiosConfig
  },
  (error) => {
    console.log(error)
  })
request.interceptors.response.use((response) => {
  console.log('响应拦截器');
  return response.data;
}, (error) => {
  console.log('响应拦截器');
  ElMessage.error({
    message: error.message,
    type: 'error'
  })
  // endLoading();
  return Promise.reject(error)
})

export default request;
