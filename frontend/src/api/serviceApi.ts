import { http } from "./http"

export const serviceApi = {
    list: () => http.get("/services"),
    findById: (id: any) => http.get(`/services/${id}`),
    create: (body: any) => http.get("/services", body),
    update: (id: any, body: any) => http.put(`/services/${id}`, body),
    delete: (id: any) => http.delete(`/services/${id}`)
}