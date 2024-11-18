import api from "@/api/api";

export const getAnomalyProductDetail = (prdId) => {
    return api.get(`/products/${prdId}`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("상품 정보를 불러오는 데 실패했습니다.", error);
            throw error;
        });
};

