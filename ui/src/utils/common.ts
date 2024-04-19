// 上传文件
import axios from "axios";

export const uploadFile = (item: any) => {
    const formData = new FormData();
    formData.append("file", item);
    let config = {
        method: "post",
        url: "/api/fleet/driver/importDriver",
        headers: {
            Accept: "*/*",
            Connection: "keep-alive",
            "Content-Type":
                "multipart/form-data; boundary=--------------------------725311584525032455700542",
        },
        data: formData,
    };
    axios(config)
        .then(function (response) {
            return response.data;
        })
        .catch(function (error) {
            console.log(error);
        });
}