<template>
  <div class="header">
    <div class="header_name">
      <!-- {{ page_name }} -->
    </div>
    <div class="upload">
      <!-- <form
        action="http://localhost:12345/uploadFiles"
        enctype="multipart/form-data"
        method="post"
      > -->
      <button size="small" class="upload-btn">
        <input
        style=""
        class="upload-content"
          ref="fileInput"
          multiple
          type="file"
          webkitdirectory
          @change="handleFileChange"
        />
      </button>
      <!-- </form> -->
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
// import { logout } from "../api/authentication/login";
import axios from "axios";

export default defineComponent({
  name: "header",
  data() {
    return {
      data: "",
      user: {
        username: "yueyue",
        roleId: 2,
        roleName: "系统管理员",
      },
      page_name: "首页",
      files: [],
    };
  },
  mounted() {
    // this.ready();
  },
  methods: {
    ready() {
      let route = this.$route.path;
      switch (route) {
        case "/authentication/main":
          this.page_name = "首页";
          break;
        case "/fleet/team":
          this.page_name = "车队管理";
          break;
        case "/fleet/truck":
          this.page_name = "车辆管理";
          break;
        case "/fleet/driver":
          this.page_name = "驾驶员管理";
          break;
        default:
          this.page_name = "0";
          break;
      }
    },
    handleFileChange(event) {
      this.files = event.target.files;
      this.uploadFiles();
    },
    uploadFiles() {
      if (!this.files) {
        console.error("No files selected");
        return;
      }

      let formData = new FormData();
      for (let i = 0; i < this.files.length; i++) {
        // 只上传java文件
        if (this.files[i].name.endsWith(".java")) {
          formData.append("files", this.files[i]);
        }
      }
      if (formData.entries().next().done) {
        // 选择的文件夹中没有.java文件
        window.alert("请选择Java项目！");
      } else {
        let config = {
          method: "post",
          url: "http://192.168.3.209:12345/uploadFiles",
          data: formData,
        };
        axios(config)
          .then(function (response) {
            let uuid = response.data.data;
            console.log(uuid);
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
  },
});
</script>

<style lang="scss" scoped="scoped">
@import "../assets/style/components/header";
</style>
