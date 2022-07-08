Vue.component("manager-trainings", {
    data: function () {

        return {
            facilityList: "",
            sortDirection: null,
            configHeaders: {
                headers: {
                    token: $cookies.get("token")
                }
            },
            userInfo: {
                username: "",
                role: ""
            },
            orders: "",
            canCancel: true

        }
    },
    template: ` 
<div>
    <div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
        <button v-on:click="editProfile">Profile</button>
    </div>
    
    <h1>Trainings</h1>   
    <div v-for="order in orders" style="display: inline-block; border: 1px solid black; margin: 10px; padding: 10px">
        <p>Name: {{order.name}}</p>
        <p>Type: {{order.type}}</p>
        <p>Facility: {{order.facilityName}}</p>
        <p>Time: {{order.time}}</p>
    </div>
	
</div>		  
`
    ,
    methods:
        {
            editProfile() {
                router.push("/edit-profile")
            }
        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('/users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                })
        }
        axios
            .get('/manager/get-facility/get-trainings',this.configHeaders)
            .then(response => this.orders = response.data.orders)
    },
});