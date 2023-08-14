<script setup lang="ts">
import '@wangeditor/editor/dist/css/style.css' // 引入 css

// @ts-ignore
import {Editor, Toolbar} from '@wangeditor/editor-for-vue'
import {ref, onMounted, shallowRef, onBeforeUnmount} from 'vue'
import {DeleteContent, GetContent, GetContentList, PostContent, PutContent} from "@/api/content";
import zhCn from "element-plus/dist/locale/zh-cn.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {GetPolicyList, GetPostsList, UploadImage} from "@/api/Policy";
import {Content} from "@/api";

const showLoading = ref(false);
const showDialog = ref(false);
const isEdit = ref(false);
const data = ref([]);
const info = ref({});
const page = ref(1);
const size = ref(10);
const total = ref(0);
const getContentList = () => {
  const params = {
    page: page.value,
    size: size.value,
  };
  showLoading.value = true;
  GetContentList(params).then(res => {
    console.log(res);
    total.value = res.total;
    data.value = res.items;
  }).finally(() => {
    showLoading.value = false;
  })
}
const getContent = (scope: any) => {
  console.log(scope);
}

const type = ref("Content");

const postContent = () => {
  isEdit.value = false;
  info.value = {
    sort: 0,
    type: "Content",
  };
  showDialog.value = true;
  // const data = {
  //   "apiVersion": "twhzx.halo.run/v1alpha1",
  //   "article": "",
  //   "content": "this is a test",
  //   "keywords": "title, age",
  //   "kind": "Content",
  //   "sort": 0,
  //   "title": "title",
  //   "type": type.value,
  //   "url": "",
  //   "metadata": {
  //     "generateName": "title"
  //   }
  // }
  // PostContent(data).then(res => {
  //   console.log(res);
  //   getContentList();
  // })
}
const deleteContent = (name: string) => {
  DeleteContent(name).then((res) => {
    console.log(res);
    getContentList();
    ElMessage.success("删除成功");
  }).catch(err => {
    console.log(err);
  })
}
const putContent = (name: string) => {
  GetContent(name).then((res: any) => {
    info.value = res;
    isEdit.value = true;
    showDialog.value = true;
    console.log(res);
  })
}
const deleteContentTip = (scope: any) => {
  ElMessageBox.confirm(`确认删除${scope.row.title}吗？`, "提示", {
    showClose: true,
  }).then(() => {
    deleteContent(scope.row.metadata.name);
  }).catch(() => {

  })
  console.log(scope);
}
getContentList();
const handleSizeChange = (val: number) => {
  size.value = val;
  getContentList();
}
const handleCurrentChange = (val: number) => {
  page.value = val;
  getContentList();
}
const editorRef = shallowRef()

// 内容 HTML
const valueHtml = ref('<p>hello</p>')

// 模拟 ajax 异步获取内容
onMounted(() => {
  setTimeout(() => {
    valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
  }, 1500)
})

const toolbarConfig = {}
const editorConfig = {
  MENU_CONF: {
    uploadImage: {
      // 自定义上传
      async customUpload(file: File, insertFn: Function) {  // TS 语法
        // async customUpload(file, insertFn) {                   // JS 语法
        // file 即选中的文件
        // 自己实现上传，并得到图片 url alt href
        // 最后插入图片
        console.log(file);
        const res: any = await uploadImg(file);
        insertFn(res.metadata.annotations["storage.halo.run/uri"], res.spec.displayName, "")
        console.log(res);
      }
    }
  },
  placeholder: '请输入内容...'
}

const uploadImg = (file: File) => {
  let data = new FormData();
  data.append("file", file);
  data.append("policyName", policyType.value);
  return UploadImage(data);
}

// 组件销毁时，也及时销毁编辑器
onBeforeUnmount(() => {
  const editor = editorRef.value
  if (editor == null) return
  editor.destroy()
})

const handleCreated = (editor) => {
  editorRef.value = editor // 记录 editor 实例，重要！
}

const mode = ref("default");
const changeType = (val: string) => {
  console.log(val);
  switch (val) {
    case 'Content':
      info.value.url = "";
      info.value.article = "";
      break;
    case 'Article':
      info.value.url = "";
      info.value.content = "";
      break;
    case 'Link':
      info.value.article = "";
      info.value.content = "";
      break;
    default:
      info.value.url = "";
      info.value.article = "";
      info.value.content = "";
  }
}
const policyType = ref("default-policy");
const changePolicyType = (val: string) => {
  console.log(val);
}

const changeArticleType = (val: string) => {
  console.log(val);
}
const getPolicyList = () => {
  const params = {
    page: 0,
    size: 0,
  }
  GetPolicyList(params).then(res => {
    policyOptionsList.value = res.items;
    console.log(res);
  })
}

const getPostsList = () => {
  const params = {
    page: 0,
    size: 0,
  }
  GetPostsList(params).then(res => {
    postOptionsList.value = res.items;
    console.log(res);
  })
}

const openDialog = () => {
  getPolicyList();
  getPostsList();
}
const optionsList = ref(<any>[]);
optionsList.value = [
  {
    label: "内容",
    value: "Content"
  },
  {
    label: "链接",
    value: "Link"
  },
  {
    label: "文章",
    value: "Article"
  }
]
const policyOptionsList = ref([]);
const postOptionsList = ref([]);
const cancelSave = () => {
  info.value = {};
  showDialog.value = false;
}
const saveOrUpdate = () => {
  const data = JSON.parse(JSON.stringify(info.value));
  if (!isEdit.value) {
    data.apiVersion = "twhzx.halo.run/v1alpha1";
    data.kind = "Content";
    data.metadata = {};
    data.metadata.generateName = data.title;
    PostContent(data).then(res => {
      ElMessage.success("新增成功");
      showDialog.value = false;
      console.log(res);
      getContentList();
    })
  } else {
    PutContent(data.metadata.name, data).then(res => {
      ElMessage.success("保存成功");
      showDialog.value = false;
      console.log(res);
      getContentList();
    })
  }
}
</script>

<template>
  <el-config-provider :locale="zhCn">
    <div v-loading="showLoading" element-loading-spinner="">
      <div class="tool-header">
        <el-button type="primary" plain @click="postContent">新增</el-button>
      </div>
      <el-table stripe border :data="data" show-overflow-tooltip>
        <el-table-column label="标题" prop="title"></el-table-column>
        <el-table-column label="关键字" prop="keywords"></el-table-column>
        <el-table-column label="类型" prop="type">
          <template #default="scope">
            <el-tag type="success" v-if="scope.row.type === 'Content'">内容</el-tag>
            <el-tag type="info" v-if="scope.row.type === 'Link'">链接</el-tag>
            <el-tag type="warning" v-if="scope.row.type === 'Article'">文章</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="内容" prop="content">
          <template #default="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </el-table-column>
        <el-table-column label="链接" prop="url"></el-table-column>
        <el-table-column label="文章" prop="article"></el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template #default="scope">
            <el-button link type="primary" size="small" @click="putContent(scope.row.metadata.name)">修改</el-button>
            <el-button link type="danger" size="small" @click="deleteContentTip(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        lang="zh"
        v-model:current-page="page"
        :page-size="size"
        small
        layout="total, prev, pager, next"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
      <el-dialog class="home-dialog" v-model="showDialog" :title="isEdit ? '修改': '新增'"
                 :close-on-click-modal="false"
                 :close-on-press-escape="false" @open="openDialog">
        <template #default>
          <el-form label-width="120px">
            <el-form-item label="标题">
              <el-input v-model.trim="info.title" placeholder="请输入标题"></el-input>
            </el-form-item>
            <el-form-item label="关键字">
              <el-input v-model.trim="info.keywords" placeholder="请输入关键字用,隔开"></el-input>
            </el-form-item>
            <el-form-item label="类型">
              <el-select v-model="info.type" @change="changeType">
                <el-option v-for="(item, index) in optionsList" :label="item.label" :value="item.value"
                           :key="index"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="排序">
              <el-input v-model.trim.number="info.sort" placeholder="请输入排序号"></el-input>
            </el-form-item>
            <el-form-item v-show="info.type === 'Article'" label="文章">
              <el-select v-model="info.article" @change="changeArticleType"
                         placeholder="请选择文章">
                <el-option v-for="(item, index) in postOptionsList" :label="item.post.spec.title"
                           :value="item.post.spec.slug" :key="index"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-show="info.type === 'Link'" label="链接">
              <el-input v-model.trim="info.url" placeholder="请输入链接地址"></el-input>
            </el-form-item>
            <el-form-item v-show="info.type === 'Content'" label="图片上传策略">
              <el-select v-model="policyType" @change="changePolicyType" placeholder="请选择默认图片上传存储策略">
                <el-option v-for="(item, index) in policyOptionsList" :label="item.spec.displayName"
                           :value="item.metadata.name" :key="index"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item v-show="info.type === 'Content'" label="内容">
              <Toolbar
                :editor="editorRef"
                :defaultConfig="toolbarConfig"
                :mode="mode"
              />
              <Editor
                class="home-editor"
                v-model="info.content"
                :defaultConfig="editorConfig"
                :mode="mode"
                @onCreated="handleCreated"
              />
            </el-form-item>
          </el-form>
        </template>
        <template #footer>
          <el-button type="danger" plain @click="cancelSave">取消</el-button>
          <el-button type="primary" plain @click="saveOrUpdate">{{ isEdit ? '保存' : '新增' }}</el-button>
        </template>
      </el-dialog>

    </div>
  </el-config-provider>
</template>

<style scoped lang="scss">
.el-pagination {
  margin-top: 20px;
  margin-left: 20px;
}

.tool-header {
  width: calc(100% - 40px);
  margin: 10px 20px;
  text-align: right;
}

.home-editor {
  width: 100% !important;
  height: 100% !important;
}
</style>
<style lang="scss">

.home-dialog {
  width: 60% !important;
  height: 70% !important;
  overflow: hidden;

  .el-dialog__body {
    height: calc(100% - 120px);
    overflow: auto;
  }
}
</style>
