"use strict";(self.webpackChunksjrtproj=self.webpackChunksjrtproj||[]).push([[867],{5867:(M,x,c)=>{c.r(x),c.d(x,{SlaReportModule:()=>h});var s=c(6895),p=c(1595),u=c(2340),R=c(5226),l=c.n(R),b=c(3172),Z=c(7206),e=c(4650),T=c(6852),S=c(1837),L=c(41),d=c(4333);function N(a,i){if(1&a&&(e.TgZ(0,"tr")(1,"td"),e._uU(2),e.qZA(),e.TgZ(3,"td"),e._uU(4),e.qZA(),e.TgZ(5,"td"),e._uU(6),e.qZA(),e.TgZ(7,"td"),e._uU(8),e.qZA(),e.TgZ(9,"td"),e._uU(10),e.qZA()()),2&a){const t=i.$implicit,o=i.index,n=e.oxw(2);e.xp6(2),e.hij(" ",n.tableSize*(n.page-1)+o+1," "),e.xp6(2),e.hij(" ",t.applicationNo," "),e.xp6(2),e.hij(" ",t.applicantName," "),e.xp6(2),e.hij(" ",t.pendingAt," "),e.xp6(2),e.hij(" ",t.noOfDayDelayed," ")}}const U=function(a,i,t){return{itemsPerPage:a,currentPage:i,totalItems:t}};function C(a,i){if(1&a&&(e.ynx(0),e.TgZ(1,"table",25)(2,"thead")(3,"tr")(4,"th",26),e._uU(5," Sl."),e.qZA(),e.TgZ(6,"th",26),e._uU(7,"Application No. "),e.qZA(),e.TgZ(8,"th",26),e._uU(9,"Applicant Name"),e.qZA(),e.TgZ(10,"th",26),e._uU(11,"Pending At"),e.qZA(),e.TgZ(12,"th",26),e._uU(13,"No. Of Days Delayed"),e.qZA()()(),e.TgZ(14,"tbody"),e.YNc(15,N,11,5,"tr",27),e.ALo(16,"paginate"),e.qZA()(),e.TgZ(17,"div",28),e._uU(18,"This table displays land-application-approval-report"),e.qZA(),e.BQk()),2&a){const t=e.oxw();e.xp6(15),e.Q6J("ngForOf",e.xi3(16,1,t.landApplicationReportList,e.kEZ(4,U,t.tableSize,t.page,t.count)))}}function y(a,i){1&a&&(e.TgZ(0,"div",29),e._uU(1," No Record Found . "),e.qZA())}function q(a,i){if(1&a){const t=e.EpF();e.TgZ(0,"div",30)(1,"pagination-controls",31),e.NdJ("pageChange",function(n){e.CHM(t);const r=e.oxw();return e.KtG(r.onTableDataChange(n))}),e.qZA()()}}const k=function(){return["/officer/officer-dashboard"]};class f{constructor(i,t,o,n,r){this.route=i,this.router=t,this.commonService=o,this.vldChkLst=n,this.encDec=r,this.page=1,this.count=0,this.tableSize=10,this.indexNumber=0,this.pageSizes=[10,20,50,100,500,1e3],this.landApplicationReportList=[]=[],this.fileURL=u.N.serviceURL+"auction-report/getAuctionReportForExcel",this.participantDetails=[]=[]}ngOnInit(){setTimeout(()=>{this.applicationSlaReport(this.page,this.tableSize)},5)}changeTab(i){this.route.navigateByUrl(i)}applicationSlaReport(i,t){let o={pageNo:i,size:t};this.commonService.setLoader(!0),this.commonService.viewAll(o,"sla-report/getApplicationApprovalReport").subscribe(n=>{let r=(n=JSON.parse(n)).RESPONSE_DATA;n.RESPONSE_TOKEN==Z.HmacSHA256(r,u.N.apiHashingKey).toString()?(n=b.lW.from(r,"base64"),200==(n=JSON.parse(n.toString())).status?(this.commonService.setLoader(!1),this.landApplicationReportList=n.result,this.count=n.count):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"Something Went Wrong!"}))):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"Invalid Response"}))},n=>{this.commonService.setLoader(!1),l().fire({icon:"error",text:n})})}onTableDataChange(i){this.page=i,this.indexNumber=(this.page-1)*this.tableSize,this.applicationSlaReport(this.page,this.tableSize)}onTableSizeChange(i){this.tableSize=i.target.value,this.indexNumber=0,this.page=1}}function P(a,i){if(1&a&&(e.TgZ(0,"tr")(1,"td"),e._uU(2),e.qZA(),e.TgZ(3,"td"),e._uU(4),e.qZA(),e.TgZ(5,"td"),e._uU(6),e.qZA(),e.TgZ(7,"td"),e._uU(8),e.qZA(),e.TgZ(9,"td"),e._uU(10),e.qZA()()),2&a){const t=i.$implicit,o=i.index,n=e.oxw(2);e.xp6(2),e.hij(" ",n.tableSize*(n.page-1)+o+1," "),e.xp6(2),e.hij(" ",t.bidderformMApplicationNo," "),e.xp6(2),e.hij(" ",t.applicantName," "),e.xp6(2),e.hij(" ",t.pendingAt," "),e.xp6(2),e.hij(" ",t.noOfDayDelayed," ")}}f.\u0275fac=function(i){return new(i||f)(e.Y36(p.F0),e.Y36(p.gz),e.Y36(T.R),e.Y36(S._),e.Y36(L.q))},f.\u0275cmp=e.Xpm({type:f,selectors:[["app-landapplication-approval-process-report"]],decls:33,vars:6,consts:[[1,"inner-section"],[1,"card","card--custom"],[1,"card-heading","mb-0"],[1,"mb-3"],["aria-label","breadcrumb"],[1,"breadcrumb"],[1,"breadcrumb-item"],[3,"routerLink"],[1,"bi","bi-house"],["aria-current","page",1,"breadcrumb-item","active"],[1,""],[1,"controls-section-header","p-0"],[1,"nav","nav-tabs","mb-3","p-0"],[1,"nav-item"],["aria-current","page","href","javascript:void(0);",1,"nav-link","active",3,"click"],["aria-current","page","href","javascript:void(0);",1,"nav-link",3,"click"],["href","javascript:void(0);",1,"nav-link",3,"click"],[1,"util","d-flex","justify-content-end","mb-1"],[1,"download-btn-box"],["target","_blank","rel","noopener","title","Download Excel",1,"btn","btn-success","text-center",3,"href"],["aria-hidden","true",1,"bi","bi-file-earmark-excel"],[1,"card-body","pb-0"],[4,"ngIf","ngIfElse"],["elseData",""],["class","d-flex justify-content-end",4,"ngIf"],["aria-describedby","sourcecreation-description",1,"table","table-bordered"],["scope","col"],[4,"ngFor","ngForOf"],["id","sourcecreation-description",2,"display","none"],[1,"text-center"],[1,"d-flex","justify-content-end"],["previousLabel","Prev","nextLabel","Next",3,"pageChange"]],template:function(i,t){if(1&i&&(e.TgZ(0,"section",0)(1,"div",1)(2,"div",2)(3,"h3",3),e._uU(4,"SLA Report"),e.qZA(),e.TgZ(5,"nav",4)(6,"ol",5)(7,"li",6)(8,"a",7),e._UZ(9,"i",8),e.qZA()(),e.TgZ(10,"li",9),e._uU(11,"SLA Report"),e.qZA()()()(),e.TgZ(12,"div",10)(13,"div",11)(14,"ul",12)(15,"li",13)(16,"a",14),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-application-approval")}),e._uU(17,"Land Application Approval Process"),e.qZA()(),e.TgZ(18,"li",13)(19,"a",15),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/auction-approval")}),e._uU(20,"Auction Approval Process"),e.qZA()(),e.TgZ(21,"li",13)(22,"a",16),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-verification")}),e._uU(23,"Land Verification"),e.qZA()()()(),e.TgZ(24,"div",17)(25,"div",18)(26,"a",19),e._UZ(27,"i",20),e.qZA()()(),e.TgZ(28,"div",21),e.YNc(29,C,19,8,"ng-container",22),e.YNc(30,y,2,0,"ng-template",null,23,e.W1O),e.YNc(32,q,2,0,"div",24),e.qZA()()()()),2&i){const o=e.MAs(31);e.xp6(8),e.Q6J("routerLink",e.DdM(5,k)),e.xp6(18),e.s9C("href",t.fileURL,e.LSH),e.xp6(3),e.Q6J("ngIf",t.landApplicationReportList.length>0)("ngIfElse",o),e.xp6(3),e.Q6J("ngIf",t.landApplicationReportList.length>0)}},dependencies:[s.sg,s.O5,p.yS,d.LS,d._s]});const j=function(a,i,t){return{itemsPerPage:a,currentPage:i,totalItems:t}};function z(a,i){if(1&a&&(e.ynx(0),e.TgZ(1,"table",25)(2,"thead")(3,"tr")(4,"th",26),e._uU(5," Sl."),e.qZA(),e.TgZ(6,"th",26),e._uU(7,"Application No. "),e.qZA(),e.TgZ(8,"th",26),e._uU(9,"Applicant Name"),e.qZA(),e.TgZ(10,"th",26),e._uU(11,"Pending At"),e.qZA(),e.TgZ(12,"th",26),e._uU(13,"No. Of Days Delayed"),e.qZA()()(),e.TgZ(14,"tbody"),e.YNc(15,P,11,5,"tr",27),e.ALo(16,"paginate"),e.qZA()(),e.TgZ(17,"div",28),e._uU(18,"This table displays land-application-approval-report"),e.qZA(),e.BQk()),2&a){const t=e.oxw();e.xp6(15),e.Q6J("ngForOf",e.xi3(16,1,t.landApplicationReportList,e.kEZ(4,j,t.tableSize,t.page,t.count)))}}function D(a,i){1&a&&(e.TgZ(0,"div",29),e._uU(1," No Record Found . "),e.qZA())}function O(a,i){if(1&a){const t=e.EpF();e.TgZ(0,"div",30)(1,"pagination-controls",31),e.NdJ("pageChange",function(n){e.CHM(t);const r=e.oxw();return e.KtG(r.onTableDataChange(n))}),e.qZA()()}}const E=function(){return["/citizen/citizen-dashboard"]};class m{constructor(i,t,o,n,r){this.route=i,this.router=t,this.commonService=o,this.vldChkLst=n,this.encDec=r,this.page=1,this.count=0,this.tableSize=10,this.indexNumber=0,this.pageSizes=[10,20,50,100,500,1e3],this.landApplicationReportList=[]=[],this.fileURL=u.N.serviceURL+"auction-report/getAuctionReportForExcel",this.participantDetails=[]=[]}ngOnInit(){setTimeout(()=>{this.applicationSlaReport(this.page,this.tableSize)},5)}changeTab(i){this.route.navigateByUrl(i)}applicationSlaReport(i,t){let o={pageNo:i,size:t};this.commonService.setLoader(!0),this.commonService.viewAll(o,"sla-report/getAuctionApprovalReport").subscribe(n=>{let r=(n=JSON.parse(n)).RESPONSE_DATA;n.RESPONSE_TOKEN==Z.HmacSHA256(r,u.N.apiHashingKey).toString()?(n=b.lW.from(r,"base64"),200==(n=JSON.parse(n.toString())).status?(this.commonService.setLoader(!1),this.landApplicationReportList=n.result,this.count=n.count):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"Something Went Wrong!"}))):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"Invalid Response"}))},n=>{this.commonService.setLoader(!1),l().fire({icon:"error",text:n})})}onTableDataChange(i){this.page=i,this.indexNumber=(this.page-1)*this.tableSize,this.applicationSlaReport(this.page,this.tableSize)}onTableSizeChange(i){this.tableSize=i.target.value,this.indexNumber=0,this.page=1}}function J(a,i){if(1&a&&(e.TgZ(0,"tr")(1,"td"),e._uU(2),e.qZA(),e.TgZ(3,"td"),e._uU(4),e.qZA(),e.TgZ(5,"td"),e._uU(6),e.qZA(),e.TgZ(7,"td"),e._uU(8),e.qZA(),e.TgZ(9,"td"),e._uU(10),e.qZA(),e.TgZ(11,"td"),e._uU(12),e.qZA(),e.TgZ(13,"td"),e._uU(14),e.qZA(),e.TgZ(15,"td"),e._uU(16),e.qZA()()),2&a){const t=i.$implicit,o=i.index,n=e.oxw(2);e.xp6(2),e.hij(" ",n.tableSize*(n.page-1)+o+1," "),e.xp6(2),e.hij(" ",t.district," "),e.xp6(2),e.hij(" ",t.tahasil," "),e.xp6(2),e.hij(" ",t.village," "),e.xp6(2),e.hij(" ",t.khataNo," "),e.xp6(2),e.hij(" ",t.plotNO," "),e.xp6(2),e.hij(" ",t.noOfDayDelayed," "),e.xp6(2),e.hij(" ",t.pendingAt," ")}}m.\u0275fac=function(i){return new(i||m)(e.Y36(p.F0),e.Y36(p.gz),e.Y36(T.R),e.Y36(S._),e.Y36(L.q))},m.\u0275cmp=e.Xpm({type:m,selectors:[["app-auction-approval-process-report"]],decls:33,vars:6,consts:[[1,"inner-section"],[1,"card","card--custom"],[1,"card-heading","mb-0"],[1,"mb-3"],["aria-label","breadcrumb"],[1,"breadcrumb"],[1,"breadcrumb-item"],[3,"routerLink"],[1,"bi","bi-house"],["aria-current","page",1,"breadcrumb-item","active"],[1,""],[1,"controls-section-header","p-0"],[1,"nav","nav-tabs","mb-3","p-0"],[1,"nav-item"],["aria-current","page","href","javascript:void(0);",1,"nav-link",3,"click"],["aria-current","page","href","javascript:void(0);",1,"nav-link","active",3,"click"],["href","javascript:void(0);",1,"nav-link",3,"click"],[1,"util","d-flex","justify-content-end","mb-1"],[1,"download-btn-box"],["target","_blank","rel","noopener","title","Download Excel",1,"btn","btn-success","text-center",3,"href"],["aria-hidden","true",1,"bi","bi-file-earmark-excel"],[1,"card-body","pb-0"],[4,"ngIf","ngIfElse"],["elseData",""],["class","d-flex justify-content-end",4,"ngIf"],["aria-describedby","sourcecreation-description",1,"table","table-bordered"],["scope","col"],[4,"ngFor","ngForOf"],["id","sourcecreation-description",2,"display","none"],[1,"text-center"],[1,"d-flex","justify-content-end"],["previousLabel","Prev","nextLabel","Next",3,"pageChange"]],template:function(i,t){if(1&i&&(e.TgZ(0,"section",0)(1,"div",1)(2,"div",2)(3,"h3",3),e._uU(4,"SLA Report"),e.qZA(),e.TgZ(5,"nav",4)(6,"ol",5)(7,"li",6)(8,"a",7),e._UZ(9,"i",8),e.qZA()(),e.TgZ(10,"li",9),e._uU(11,"SLA Report"),e.qZA()()()(),e.TgZ(12,"div",10)(13,"div",11)(14,"ul",12)(15,"li",13)(16,"a",14),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-application-approval")}),e._uU(17,"Land Application Approval Process"),e.qZA()(),e.TgZ(18,"li",13)(19,"a",15),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/auction-approval")}),e._uU(20,"Auction Approval Process"),e.qZA()(),e.TgZ(21,"li",13)(22,"a",16),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-verification")}),e._uU(23,"Land Verification"),e.qZA()()()(),e.TgZ(24,"div",17)(25,"div",18)(26,"a",19),e._UZ(27,"i",20),e.qZA()()(),e.TgZ(28,"div",21),e.YNc(29,z,19,8,"ng-container",22),e.YNc(30,D,2,0,"ng-template",null,23,e.W1O),e.YNc(32,O,2,0,"div",24),e.qZA()()()()),2&i){const o=e.MAs(31);e.xp6(8),e.Q6J("routerLink",e.DdM(5,E)),e.xp6(18),e.s9C("href",t.fileURL,e.LSH),e.xp6(3),e.Q6J("ngIf",t.landApplicationReportList.length>0)("ngIfElse",o),e.xp6(3),e.Q6J("ngIf",t.landApplicationReportList.length>0)}},dependencies:[s.sg,s.O5,p.yS,d.LS,d._s]});const I=function(a,i,t){return{itemsPerPage:a,currentPage:i,totalItems:t}};function Y(a,i){if(1&a&&(e.ynx(0),e.TgZ(1,"table",24)(2,"thead")(3,"tr")(4,"th",25),e._uU(5," Sl."),e.qZA(),e.TgZ(6,"th",25),e._uU(7,"District"),e.qZA(),e.TgZ(8,"th",25),e._uU(9,"Tahasil"),e.qZA(),e.TgZ(10,"th",25),e._uU(11,"Village"),e.qZA(),e.TgZ(12,"th",25),e._uU(13,"Khata No."),e.qZA(),e.TgZ(14,"th",25),e._uU(15,"Plot No."),e.qZA(),e.TgZ(16,"th",25),e._uU(17,"No. Of Days Delayed"),e.qZA(),e.TgZ(18,"th",25),e._uU(19,"Pending At"),e.qZA()()(),e.TgZ(20,"tbody"),e.YNc(21,J,17,8,"tr",26),e.ALo(22,"paginate"),e.qZA()(),e.BQk()),2&a){const t=e.oxw();e.xp6(21),e.Q6J("ngForOf",e.xi3(22,1,t.LandVerificationList,e.kEZ(4,I,t.tableSize,t.page,t.count)))}}function F(a,i){1&a&&(e.TgZ(0,"div",27),e._uU(1," No Record Found . "),e.qZA())}function V(a,i){if(1&a){const t=e.EpF();e.TgZ(0,"div",28)(1,"pagination-controls",29),e.NdJ("pageChange",function(n){e.CHM(t);const r=e.oxw();return e.KtG(r.onTableDataChange(n))}),e.qZA()()}}const w=function(){return["/citizen/citizen-dashboard"]};class v{constructor(i,t,o,n,r){this.route=i,this.router=t,this.commonService=o,this.vldChkLst=n,this.encDec=r,this.page=1,this.count=0,this.tableSize=10,this.indexNumber=0,this.pageSizes=[10,20,50,100,500,1e3],this.LandVerificationList=[]=[],this.fileURL=u.N.serviceURL+"auction-report/getLandVerificationReportForExcel"}ngOnInit(){setTimeout(()=>{this.landVerificationSlaReport(this.page,this.tableSize)},5)}changeTab(i){this.route.navigateByUrl(i)}landVerificationSlaReport(i,t){let o={pageNo:i,size:t};this.commonService.setLoader(!0),this.commonService.viewAll(o,"sla-report/getLandVerificationReport").subscribe(n=>{let r=(n=JSON.parse(n)).RESPONSE_DATA;n.RESPONSE_TOKEN==Z.HmacSHA256(r,u.N.apiHashingKey).toString()?(n=b.lW.from(r,"base64"),200==(n=JSON.parse(n.toString())).status?(this.commonService.setLoader(!1),this.LandVerificationList=n.result,this.count=n.count):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"No record found !"}))):(this.commonService.setLoader(!1),l().fire({icon:"error",text:"Invalid Response"}))},n=>{this.commonService.setLoader(!1),l().fire({icon:"error",text:n})})}onTableDataChange(i){this.page=i,this.indexNumber=(this.page-1)*this.tableSize,this.landVerificationSlaReport(this.page,this.tableSize)}onTableSizeChange(i){this.tableSize=i.target.value,this.indexNumber=0,this.page=1}}v.\u0275fac=function(i){return new(i||v)(e.Y36(p.F0),e.Y36(p.gz),e.Y36(T.R),e.Y36(S._),e.Y36(L.q))},v.\u0275cmp=e.Xpm({type:v,selectors:[["app-land-verification-report"]],decls:33,vars:6,consts:[[1,"inner-section"],[1,"card","card--custom"],[1,"card-heading","mb-0"],[1,"mb-3"],["aria-label","breadcrumb"],[1,"breadcrumb"],[1,"breadcrumb-item"],[3,"routerLink"],[1,"bi","bi-house"],["aria-current","page",1,"breadcrumb-item","active"],[1,""],[1,"controls-section-header","p-0"],[1,"nav","nav-tabs","mb-3","p-0"],[1,"nav-item"],["aria-current","page","href","javascript:void(0);",1,"nav-link",3,"click"],["href","javascript:void(0);",1,"nav-link","active",3,"click"],[1,"util","d-flex","justify-content-end","mb-1"],[1,"download-btn-box"],["target","_blank","rel","noopener","title","Download Excel",1,"btn","btn-success","text-center",3,"href"],["aria-hidden","true",1,"bi","bi-file-earmark-excel"],[1,"card-body","pb-0"],[4,"ngIf","ngIfElse"],["elseData",""],["class","d-flex justify-content-end",4,"ngIf"],["aria-describedby","sourcecreation-description",1,"table","table-bordered"],["scope","col"],[4,"ngFor","ngForOf"],[1,"text-center"],[1,"d-flex","justify-content-end"],["previousLabel","Prev","nextLabel","Next",3,"pageChange"]],template:function(i,t){if(1&i&&(e.TgZ(0,"section",0)(1,"div",1)(2,"div",2)(3,"h3",3),e._uU(4,"SLA Report"),e.qZA(),e.TgZ(5,"nav",4)(6,"ol",5)(7,"li",6)(8,"a",7),e._UZ(9,"i",8),e.qZA()(),e.TgZ(10,"li",9),e._uU(11,"SLA Report"),e.qZA()()()(),e.TgZ(12,"div",10)(13,"div",11)(14,"ul",12)(15,"li",13)(16,"a",14),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-application-approval")}),e._uU(17,"Land Application Approval Process"),e.qZA()(),e.TgZ(18,"li",13)(19,"a",14),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/auction-approval")}),e._uU(20,"Auction Approval Process"),e.qZA()(),e.TgZ(21,"li",13)(22,"a",15),e.NdJ("click",function(){return t.changeTab("/officer/report/sla/land-verification")}),e._uU(23,"Land Verification"),e.qZA()()()(),e.TgZ(24,"div",16)(25,"div",17)(26,"a",18),e._UZ(27,"i",19),e.qZA()()(),e.TgZ(28,"div",20),e.YNc(29,Y,23,8,"ng-container",21),e.YNc(30,F,2,0,"ng-template",null,22,e.W1O),e.YNc(32,V,2,0,"div",23),e.qZA()()()()),2&i){const o=e.MAs(31);e.xp6(8),e.Q6J("routerLink",e.DdM(5,w)),e.xp6(18),e.s9C("href",t.fileURL,e.LSH),e.xp6(3),e.Q6J("ngIf",t.LandVerificationList.length>0)("ngIfElse",o),e.xp6(3),e.Q6J("ngIf",t.LandVerificationList.length>0)}},dependencies:[s.sg,s.O5,p.yS,d.LS,d._s]});const H=[{path:"land-application-approval",component:f},{path:"auction-approval",component:m},{path:"land-verification",component:v}];class g{}g.\u0275fac=function(i){return new(i||g)},g.\u0275mod=e.oAB({type:g}),g.\u0275inj=e.cJS({imports:[p.Bz.forChild(H),p.Bz]});class h{}h.\u0275fac=function(i){return new(i||h)},h.\u0275mod=e.oAB({type:h}),h.\u0275inj=e.cJS({imports:[s.ez,g,d.JX]})}}]);