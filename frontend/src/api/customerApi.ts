import { http } from "./http"

export const customerApi = {
    list: () => http.get("/customer"),
    findById: (id: any) => http.get(`/customer/id/${id}`),
    findByCpf: (cpf: any) => http.get(`/customer/cpf/${cpf}`),
    create: (body: any) => http.post("/customer", body),
    update: (id: any, body: any) => http.patch(`/customer/id/${id}`, body),
    delete: (id: any) => http.delete(`/customer/id/${id}`),
}