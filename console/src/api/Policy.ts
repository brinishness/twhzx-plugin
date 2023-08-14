import {Policy} from "@/api";
import request from "@/utils/request";

export const GetPolicyList = (params: any) => {
  return request({
    url: Policy.list,
    method: "get",
    params
  })
}

export const UploadImage = (data: any) => {
  return request({
    url: Policy.upload,
    method: "post",
    data,
    headers: {
      "Content-Type": "multipart/form-data"
    }
  })
}
export const GetPostsList = (params: any) => {
  return request({
    url: Policy.posts,
    method: "get",
    params
  })
} 
