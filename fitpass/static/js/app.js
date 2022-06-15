const FacilityList = { template: '<facility-list></facility-list>' }
const Contact = { template: '<contact></contact>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: FacilityList},
        {path: '/cnt', component: Contact}
    ]
});

var app = new Vue({
    router,
    el: '#main'
});

