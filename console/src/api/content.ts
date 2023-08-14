import {Content} from "@/api";
import request from "@/utils/request";

export const GetContentList = (params: any) => {
  return request({
    url: Content.list,
    method: "get",
    params
  })
}

export const GetContent = (name: string) => {
  return request({
    url: Content.info + `/${name}`,
    method: "get",
  })
}

export const PostContent = (data: any) => {
  return request({
    url: Content.add,
    method: "post",
    data
  })
}
export const PutContent = (name: string, data: any) => {
  return request({
    url: Content.edit + `/${name}`,
    method: "put",
    data
  })
}

export const DeleteContent = (name: string) => {
  return request({
    url: Content.delete + `/${name}`,
    method: "delete",
  })
}
