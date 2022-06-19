const FacilityList = { template: '<facility-list></facility-list>' }
const Contact = { template: '<contact></contact>' }
const CustomerRegistration = { template: '<customer-registration></customer-registration>' }
const Login = { template: '<login></login>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: FacilityList},
        {path: '/cnt', component: Contact},
        {path: '/customer-registration', component: CustomerRegistration},
        {path: '/login', component: Login}
    ]
});


var app = new Vue({
    router,
    el: '#main'
});

