import { http } from "./http"

export const petApi = {
    list: () => http.get("/pets"),
    findById: (id: any) => http.get(`/pets/${id}`),
    create: (body: any) => http.post("/pets", body),
    update: (id: any, body: any) => http.put(`/pets/${id}`, body),
    delete: (id: any) => http.delete(`/pets/${id}`)
}