import { request } from '@/utils/request';

export function metric(uuid:string) {
    return request({
        url: `/metric?uuid=${uuid}`,
        method: 'get',
    })
}
