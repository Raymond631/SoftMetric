import axios from "axios";

//导出request方法，供其它地方使用
export function request(config: any) {
  const instance = axios.create({
    baseURL: "/api",
    timeout: 50000,
    // 'transformRequest' 允许在向服务器发送前，修改请求数据
    transformRequest: [
      function (data: any) {
        // 对 data 做序列化处理
        return JSON.stringify(data);
      },
    ],
  });

  instance.defaults.headers.post["Content-Type"] = "application/json";
  //------------------请求拦截-------------------//
  instance.interceptors.request.use(
    (config: any) => {
      // 若存在token则带token
      let token = sessionStorage.getItem('token')
      if (token) {
        config.headers.satoken = token;
      }
      // 放行
      return config;
    },
    (err: any) => {
      console.log("请求拦截=>", err);
      return err;
    }
  );

  //------------------响应拦截-------------------//
  instance.interceptors.response.use(
    (res: any) => {
      console.log("响应信息=>", res);
      let alterMessage = '';
      let errorCode = res.data.code;
      switch (res.data.code) {
        case 400:
          alterMessage = "错误请求";
          break;
        case 401:
          alterMessage = "未登录";
          break;
        case 403:
          alterMessage = "无权限";
          break;
        case 404:
          alterMessage = "请求错误,未找到该资源";
          // window.location.href = "/error";
          break;
        case 405:
          alterMessage = "请求方法未允许";
          break;
        case 500:
          alterMessage = "系统内部错误,暂无数据";
          break;
        case 501:
          alert(res.data.msg)
      }
      if (alterMessage !== '') {
        alert(alterMessage);
        let token = sessionStorage.getItem('token')
        if (token) {

          window.location.href = "/knowledge/index"
        }
        else {

          window.location.href = "/";
        }
      }

      return res ? res.data : res;
    },
    (err: any) => {
      console.log("响应拦截=>", err);
      if (err && err.response) {
        // 1.公共错误处理
      } else {
        // 超时处理
        if (JSON.stringify(err).includes("timeout")) {
          // alert("服务器响应超时，请刷新当前页");
          // window.location.href = "/error";
        }
        err.message = "连接服务器失败";
      }
      alert("错误");
      throw err;
    }
  );

  return instance(config);
}




