Vue.component("users-list", {
    data: function () {

        return {
            usersDto: "",
            userInfo: {
                username: "",
                role: ""
            },
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            form: {
                searchInput: "",
                userRole: "",
                userType: "",
            },
            sortDirection: null,
            sortIndex: null,
        }
    },
    template: ` 
<div>
    <label>Username: {{userInfo.username}}</label>
    
    <input type="text" placeholder="Search registered Users"  v-model="form.searchInput" v-on:input="searchUsers()" > 
     <select name="type" v-model="form.userRole" v-on:change="searchUsers()">
      <option value="">Select Role</option>
      <option value="ADMINISTRATOR">ADMINISTRATOR</option>
      <option value="MANAGER">MANAGER</option>
      <option value="TRAINER">TRAINER</option>
      <option value="CUSTOMER">CUSTOMER</option>
    </select>
    <select name="type" v-model="form.userType" v-on:change="searchUsers()">
      <option value="">Select Type</option>
      <option value="BRONZE">BRONZE</option>
      <option value="SILVER">SILVER</option>
      <option value="GOLD">GOLD</option>
    </select>
	<table>
	<tr>
	    <th></th>
		<th v-on:click="sortList(0)">Name</th>
		<th v-on:click="sortList(1)">Surname</th>
		<th v-on:click="sortList(2)">Username</th>
		<th>Role</th>
		<th>Gender</th>
		<th>Birth</th>
	</tr>
		
	<tr v-for="u in usersDto.users" >
		<td></td>
		<td>{{u.name}}</td>
		<td>{{u.surname}}</td>
		<td>
		    {{u.username}}
		</td>
		<td>{{u.role}}</td>
		<td>
		    {{u.gender}}
		</td>
		<td>
		    {{u.birth}}
        </td>
	</tr>
</table>
	<button class="login-button" v-on:click="logout">Odjavi se</button>
	<button class="login-button" v-on:click="createTrainer">Create trainer</button>
	<button class="login-button" v-on:click="createManager">Creater manager</button>
</div>		  
`
    ,
    methods:
        {
            logout: function () {
                $cookies.remove('token')
                router.push('/login')
            },
            createTrainer: function () {
                router.push('/create-trainer')
            },
            createManager: function () {
                router.push('/create-manager')
            },
            searchUsers() {
                axios.get('/administrator/search-users?searchInput=' + this.form.searchInput + '&userRole=' + this.form.userRole + '&userType=' + this.form.userType, this.configHeaders)
                    .then(response => {
                        this.usersDto = response.data;
                    })
                this.sortDirection = null;
                this.sortIndex = null;
            },

            sortList(indexCol) {
                if (this.sortIndex === indexCol) {
                    if (this.sortDirection === "desc") {
                        this.sortDirection = "asc";
                        this.sorting(indexCol);
                    } else {
                        this.sortDirection = "desc";
                        this.sorting(indexCol);
                    }
                } else {
                    this.sortIndex = indexCol;
                    this.sortDirection = "asc";
                    this.sorting(indexCol);
                }
            },

            sorting(indexCol) {
                console.log(indexCol);
                console.log(this.sortDirection);
                axios.get('/administrator/sort-search-users?searchInput=' + this.form.searchInput + '&userRole=' + this.form.userRole + '&userType=' +
                    this.form.userType + '&columnIndex=' + indexCol + '&sortDir=' + this.sortDirection,this.configHeaders)
                    .then(response => {
                        this.usersDto = response.data;
                    })
            },
        },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios.post('users/get-info', $cookies.get("token"),this.configHeaders)
            .then(response => {
                this.userInfo = response.data
                if (this.userInfo.role !== 'ADMINISTRATOR') {
                    router.push("/")
                }
            })
        axios
            .get('/administrator/get-users', this.configHeaders)
            .then(response => {
                this.usersDto = response.data
            })


    },
});