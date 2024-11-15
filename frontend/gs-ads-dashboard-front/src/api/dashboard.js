import api from "@/api/api";

export const getCount = () => {
    return api.get(`/products/count`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("전체 상품의 개수를 불러올 수 없습니다.", error);
            throw error;
        });
}

export const getScheduleCount = () => {
    return api.get(`/products/scheduled-count`)
        .then((response) => {
            return response.data;
        })
        .catch((error) => {
            console.error("대기 중인 상품의 개수를 불러올 수 없습니다.", error);
            throw error;
        });
}

export const getAnomalyProducts = (viewName = null, code = [], totalScore = null, page = 0) => {
    return api.get('/products', {
        params: {
            viewName,
            code,
            totalScore
        },
    })
    .then((response) => {
        return response.data;
    })
    .catch((error) => {
        console.error("이상 상품 리스트를 불러올 수 없습니다.", error);
        throw error;
    });
};
