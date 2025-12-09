import { http } from "./http"

export const schedulingAPi = {
    list: () => http.get("/scheduling"),
    findById: (id: any) => http.get(`/scheduling/${id}`),
    create: (body: any) => http.post("/scheduling", body),
    update: (id: any, body: any) => http.put(`/scheduling/${id}`, body),
    delete: (id: any) => http.delete(`/scheduling/${id}`)
}