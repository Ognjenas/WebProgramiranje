const FacilityList = { template: '<facility-list></facility-list>' }
const Contact = { template: '<contact></contact>' }
const CustomerRegistration = { template: '<customer-registration></customer-registration>' }
const Login = { template: '<login></login>' }
const FacilityCreate = { template: '<facility-create></facility-create>' }
const UsersList = { template: '<users-list></users-list>' }
const CreateTrainer = { template: '<create-trainer></create-trainer>' }
const CreateManager = { template: '<create-manager></create-manager>' }

const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: FacilityList},
        {path: '/contact', component: Contact},
        {path: '/customer-registration', component: CustomerRegistration},
        {path: '/login', component: Login},
        {path: '/facility-create', component: FacilityCreate},
        {path: '/users-list', component: UsersList},
        {path: '/create-trainer', component: CreateTrainer},
        {path: '/create-manager', component: CreateManager}
    ]
});


var app = new Vue({
    router,
    el: '#main'
});

