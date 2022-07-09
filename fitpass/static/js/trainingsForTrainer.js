Vue.component("trainer-trainings", {
    data: function () {

        return {
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
            canCancel: true,

            searchForm:{
                facName:"",
                price:"",
                facType:"",
                trainingType:"",
                sortType:"",
                sortDir:"",
                fromDate:"",
                toDate:"",
            },
            errorSort:"",
            errorDate:"",
            validSubmit:false,

        }
    },
    template: ` 
<div>
    <div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
        <button v-on:click="editProfile">Profile</button>
    </div>
    
    <h1>Trainings</h1>   
    <table>
    <tr>
    SEARCH AND FILTER:
    <td><input name="facName" placeholder="Search by Facility name"  v-model="searchForm.facName" v-on:input="searchTrainings"></td>
    <td>
    <select name="priceRange" v-model="searchForm.price" v-on:change="searchTrainings">
      <option value="">Select Price Range</option>
      <option value="0"><1000</option>
      <option value="1">1000-5000</option>
      <option value="2">5000-10000</option>
      <option value="3">>10000</option>
    </select>
    </td>
    <td><input type="date" placeholder="From Date" v-model = "searchForm.fromDate" name="fromDate" v-on:change="searchTrainings"> </td>
    <td><input type="date" placeholder="To Date" v-model = "searchForm.toDate" name="toDate" v-on:change="searchTrainings">{{errorDate}}</td>
    <td>
    <select name="facType" v-model="searchForm.facType" v-on:change="searchTrainings">
      <option value="">Select Type</option>
      <option value="GYM">GYM</option>
      <option value="FIGHTING_GYM">FIGHTING_GYM</option>
      <option value="SWIMMING_POOL">SWIMMING_POOL</option>
      <option value="SPORTS_CENTER">SPORTS_CENTER</option>
      <option value="DANCE_STUDIO">DANCE_STUDIO</option>
      <option value="STADIUM">STADIUM</option>
    </select>
    </td>
    <td>
    <select name="trainingType" v-model="searchForm.trainingType" v-on:change="searchTrainings">
      <option value="">Select Training Type</option>
      <option value="TRAINING">TRAINING</option>
      <option value="SAUNA">SAUNA</option>
      <option value="SWIMMING_POOL">SWIMMING_POOL</option>
      </select>
      </td>
    </tr>
    SORT:
    <tr>
        <td>
      <select name="sortType" v-model="searchForm.sortType" v-on:change="searchTrainings">
      <option value="">Sort By</option>
      <option value="0">Facility Name</option>
      <option value="1">Training Price</option>
      <option value="2">Training Date</option>
      </select>
      </td>
      <td>
      <select name="sortDir" v-model="searchForm.sortDir" v-on:change="searchTrainings">
      <option value="">Sort Direction</option>
      <option value="asc">Ascending</option>
      <option value="desc">Descending</option>
      </select>
      {{errorSort}}
      </td>
    </tr>
    </table>
    
    <div v-for="order in orders" style="display: inline-block; border: 1px solid black; margin: 10px; padding: 10px">
        <p>Name: {{order.name}}</p>
        <p>Type: {{order.type}}</p>
        <p>Facility: {{order.facilityName}}</p>
        <p>Time: {{order.time}}</p>
        <button v-if="order.type == 'PERSONAL'" v-on:click="cancel(order.id)">Cancel</button>
        <p v-if="!canCancel">You can't cancel in less than 2 days!</p>
    </div>
	 <button v-if="validSubmit" type="button" v-on:click="consLog">LOG</button>
</div>		  
`
    ,
    methods:
        {
            editProfile() {
                router.push("/edit-profile")
            },
            cancel(id) {
                axios
                    .get('/trainer/get-trainings/cancel?id='+id, this.configHeaders)
                    .then(response => {
                        this.canCancel = response.data;
                        if(this.canCancel) {
                            location.reload();
                        }
                    })
            },

            consLog(){
                console.log(this.searchForm.facName);
                console.log(this.searchForm.price);
                console.log(this.searchForm.facType);
                console.log(this.searchForm.trainingType);
                console.log(this.searchForm.sortType);
                console.log(this.searchForm.sortDir);
                console.log(this.searchForm.fromDate);
                console.log(this.searchForm.toDate);
            },

            searchTrainings(){
                console.log("CHANGE")
                if((this.searchForm.sortDir==="" && this.searchForm.sortType==="")||(this.searchForm.sortDir!=="" && this.searchForm.sortType!=="")){
                    if((this.searchForm.fromDate==="" && this.searchForm.toDate==="")||(this.searchForm.fromDate!=="" && this.searchForm.toDate!=="")){
                        this.errorDate=""
                        this.errorSort=""
                        this.validSubmit=true;
                    }else{
                        this.errorDate="Please select FROM and TO date!";
                        this.validSubmit=false;
                    }
                }else{
                    this.errorSort="Please select Order BY and DIRECTION!"
                    this.validSubmit=false;
                }

                if(this.validSubmit){
                    console.log("USO U SUBMIT")
                    axios.get('/trainer/search-trainings?facName='+this.searchForm.facName+'&price='+this.searchForm.price+'&facType='+this.searchForm.facType
                        +'&trainingType='+this.searchForm.trainingType+'&sortType='+this.searchForm.sortType+'&sortDir='+this.searchForm.sortDir+
                        '&fromDate='+this.searchForm.fromDate+'&toDate='+this.searchForm.toDate+'&username='+this.userInfo.username,this.configHeaders)
                        .then(response=>{
                            this.orders=response.data.orders;
                        })
                }
            },
        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('/users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                })
        }
        axios
            .get('/trainer/get-trainings',this.configHeaders)
            .then(response => this.orders = response.data.orders)
    },
});