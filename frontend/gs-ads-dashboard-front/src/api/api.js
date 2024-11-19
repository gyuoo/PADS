import axios from "axios";

const api = axios.create({
    baseURL: "http://k11s103.p.ssafy.io:8081/api/v1" ,
    withCredentials: true
});

export default api;