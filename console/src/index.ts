import { definePlugin } from "@halo-dev/console-shared";
import { IconPlug } from "@halo-dev/components";
import { markRaw } from "vue";
import "normalize.css";
import 'element-plus/theme-chalk/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import Home from "@/views/Home.vue";
export default definePlugin({
  components: {},
  routes: [
    {
      parentName: "Root",
      route: {
        path: "/twhzx-plugin",
        name: "TwhzxPlugin",
        component: Home,
        meta: {
          title: "内容管理",
          searchable: true,
          menu: {
            name: "内容管理",
            group: "内容管理",
            icon: markRaw(IconPlug),
            priority: 0,
          },
        },
      },
    },
  ],
  extensionPoints: {},
});
