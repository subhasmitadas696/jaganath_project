"use strict";(self.webpackChunksjrtproj=self.webpackChunksjrtproj||[]).push([[884],{6884:(U,m,i)=>{i.r(m),i.d(m,{ApplicationHistoryReportModule:()=>s});var u=i(6895),g=i(1595),_=i(5226),h=i.n(_),t=i(4650),b=i(1469),f=i(41),x=i(6852),d=i(4333),v=i(6136);function A(o,n){if(1&o&&(t.ynx(0),t.TgZ(1,"span"),t._uU(2),t.qZA(),t.TgZ(3,"span"),t._uU(4),t.qZA(),t.TgZ(5,"div")(6,"span",26),t._uU(7),t.qZA(),t.TgZ(8,"span",33),t._uU(9),t.qZA()(),t.BQk()),2&o){const e=t.oxw().$implicit;t.xp6(2),t.hij("",e.mouzaName,",\xa0"),t.xp6(2),t.Oqu(e.tehsilName),t.xp6(3),t.hij("District : ",e.districtName," ,"),t.xp6(2),t.hij(" Khata : ",e.khataNo," ")}}function Z(o,n){1&o&&t._uU(0," -- ")}function y(o,n){if(1&o){const e=t.EpF();t.TgZ(0,"tr")(1,"td"),t._uU(2),t.qZA(),t.TgZ(3,"td")(4,"span",24),t._uU(5),t.qZA(),t.TgZ(6,"span",25),t._uU(7),t.qZA(),t.TgZ(8,"span",26),t._uU(9),t.ALo(10,"masking"),t.qZA(),t.TgZ(11,"span",27),t._uU(12),t.qZA()(),t.TgZ(13,"td"),t.YNc(14,A,10,4,"ng-container",20),t.YNc(15,Z,1,0,"ng-template",null,28,t.W1O),t.qZA(),t.TgZ(17,"td"),t._uU(18),t.qZA(),t.TgZ(19,"td"),t._uU(20),t.qZA(),t.TgZ(21,"td",29)(22,"div",30)(23,"button",31),t.NdJ("click",function(){const r=t.CHM(e).$implicit,N=t.oxw(2);return t.KtG(N.viewMore(r.landApplicantId))}),t._UZ(24,"i",32),t.qZA()()()()}if(2&o){const e=n.$implicit,a=n.index,p=t.MAs(16),r=t.oxw(2);t.xp6(2),t.Oqu(a+1+r.indexNumber),t.xp6(3),t.Oqu(e.applicantNo),t.xp6(2),t.Oqu(e.applicantName),t.xp6(2),t.Oqu(t.lcZ(10,11,e.mobileNo)),t.xp6(2),t.Tol(4==e.applicationStatusId?"status--complete":5==e.applicationStatusId||6==e.applicationStatusId||7==e.applicationStatusId?"status--reject":8==e.applicationStatusId||9==e.applicationStatusId||10==e.applicationStatusId?"status--inprogress":"status--pending"),t.xp6(1),t.hij(" ",null!=e.applicationStatus?e.applicationStatus:"Draft",""),t.xp6(2),t.Q6J("ngIf",null!=e.districtName)("ngIfElse",p),t.xp6(4),t.hij(" ",null!=e.applicationFlow?e.applicationFlow:"N/A"," "),t.xp6(2),t.hij(" ",null!=e.remark?e.remark:"N/A"," ")}}const C=function(o,n,e){return{itemsPerPage:o,currentPage:n,totalItems:e}};function T(o,n){if(1&o&&(t.ynx(0),t.YNc(1,y,25,13,"tr",23),t.ALo(2,"paginate"),t.BQk()),2&o){const e=t.oxw();t.xp6(1),t.Q6J("ngForOf",t.xi3(2,1,e.applicationDetails,t.kEZ(4,C,e.tableSize,e.page,e.count)))}}function O(o,n){1&o&&(t.TgZ(0,"tr")(1,"td",34),t._uU(2,"No Record Found"),t.qZA()())}function M(o,n){if(1&o){const e=t.EpF();t.TgZ(0,"div",35)(1,"pagination-controls",36),t.NdJ("pageChange",function(p){t.CHM(e);const r=t.oxw();return t.KtG(r.onTableDataChange(p))}),t.qZA()()}}class l{constructor(n,e,a,p,r){this.citizenService=n,this.router=e,this.activatedRoute=a,this.encdec=p,this.commonService=r,this.page=1,this.count=0,this.tableSize=10,this.indexNumber=0,this.pageSizes=[10,20,50,100,500,1e3],this.applicationDetails=[]=[]}ngOnInit(){setTimeout(()=>{this.getAppHistoryReport()},5)}getAppHistoryReport(){let n={pageNumber:this.page,pageSize:this.tableSize};this.commonService.setLoader(!0),this.commonService.viewAllWithoutEncode(n,"api/landservices/getactivityhistoryreport").subscribe(e=>{this.commonService.setLoader(!1),200==(e=JSON.parse(e)).status?(this.applicationDetails=e.result.dtodata,this.count=e.count):h().fire({icon:"error",text:"Something Went Wrong!"})},e=>{this.commonService.setLoader(!1),h().fire({icon:"error",text:e})})}gotToDashboard(){this.commonService.goToOwnDashboard()}onTableDataChange(n){this.page=n,this.indexNumber=(this.page-1)*this.tableSize,this.getAppHistoryReport()}onTableSizeChange(n){this.tableSize=n.target.value,this.page=1,this.indexNumber=0}viewMore(n){let e=this.encdec.encText(n.toString());this.router.navigateByUrl("/officer/app-history/"+e)}}l.\u0275fac=function(n){return new(n||l)(t.Y36(b.m),t.Y36(g.F0),t.Y36(g.gz),t.Y36(f.q),t.Y36(x.R))},l.\u0275cmp=t.Xpm({type:l,selectors:[["app-app-history"]],decls:37,vars:3,consts:[[1,"inner-section"],[1,"card","card--custom"],[1,"card-heading","mb-3"],[1,"mb-3"],["aria-label","breadcrumb"],[1,"breadcrumb"],[1,"breadcrumb-item"],[3,"click"],["aria-hidden","true",1,"bi","bi-house"],["aria-current","page",1,"breadcrumb-item","active"],[1,"controls-section-header","p-0"],["id","ex1-content",1,"tab-content"],["id","ex1-tabs-1","role","tabpanel","aria-labelledby","ex1-tab-1",1,"tab-pane","fade","show","active"],[1,"table-responsive"],["aria-describedby","appliedland-description","aria-hidden","true",1,"table","table-bordered"],[1,"table-info"],["scope","col","width","5"],["width","","scope","col"],["scope","col"],["scope","col",1,"text-center"],[4,"ngIf","ngIfElse"],["elseTr",""],["class","d-flex justify-content-end",4,"ngIf"],[4,"ngFor","ngForOf"],[1,"fw-bold","mb-2"],[1,"d-block","text-grey"],[1,"text-grey"],[1,"d-block","fs-small","text-grey","status"],["elseTd",""],[1,"text-center"],[1,"d-flex"],["title","View More",1,"btn","outline-btn","outline-btn--view","me-2",3,"click"],[1,"icon-view"],[1,"text-grey","d-block"],["colspan","6",1,"text-center"],[1,"d-flex","justify-content-end"],["previousLabel","Prev","nextLabel","Next",3,"pageChange"]],template:function(n,e){if(1&n&&(t.TgZ(0,"section",0)(1,"div",1)(2,"div",2)(3,"h3",3),t._uU(4,"Application History Report"),t.qZA(),t.TgZ(5,"nav",4)(6,"ol",5)(7,"li",6)(8,"a",7),t.NdJ("click",function(){return e.gotToDashboard()}),t._UZ(9,"i",8),t.qZA()(),t.TgZ(10,"li",9),t._uU(11,"Application History Report"),t.qZA()()()(),t._UZ(12,"div",10),t.TgZ(13,"div",11)(14,"div",12)(15,"div")(16,"div",13)(17,"table",14)(18,"thead",15)(19,"tr")(20,"th",16),t._uU(21,"Sl#"),t.qZA(),t.TgZ(22,"th",17),t._uU(23,"Applicant Details"),t.qZA(),t.TgZ(24,"th",18),t._uU(25,"Land Details"),t.qZA(),t.TgZ(26,"th",18),t._uU(27," Application Flow"),t.qZA(),t.TgZ(28,"th",18),t._uU(29,"Remark "),t.qZA(),t.TgZ(30,"th",19),t._uU(31,"Action"),t.qZA()()(),t.TgZ(32,"tbody"),t.YNc(33,T,3,8,"ng-container",20),t.YNc(34,O,3,0,"ng-template",null,21,t.W1O),t.qZA()()()(),t.YNc(36,M,2,0,"div",22),t.qZA()()()()),2&n){const a=t.MAs(35);t.xp6(33),t.Q6J("ngIf",e.applicationDetails.length>0)("ngIfElse",a),t.xp6(3),t.Q6J("ngIf",e.applicationDetails.length>0)}},dependencies:[u.sg,u.O5,d.LS,d._s,v.F],styles:['.util-set[_ngcontent-%COMP%]{display:flex;justify-content:flex-end}.util-set[_ngcontent-%COMP%]   .util-icon[_ngcontent-%COMP%]{margin-left:.5rem;margin-bottom:1.5rem}.util-set[_ngcontent-%COMP%]   .util-icon[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]{border:1px solid var(--dark-green);border-radius:var(--radius-6);padding:.2rem .5rem}.util-set[_ngcontent-%COMP%]   .util-icon[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]   i[_ngcontent-%COMP%]{color:var(--dark-green)}.util-set[_ngcontent-%COMP%]   .util-icon[_ngcontent-%COMP%]   a[_ngcontent-%COMP%]:hover{box-shadow:#3c40434d 0 1px 2px,#3c404326 0 2px 6px 2px}.outline-btn[_ngcontent-%COMP%]{border-color:var(--dark-green);color:var(--dark-green);transition:all ease .3s}.outline-btn[_ngcontent-%COMP%]:hover{background-color:var(--dark-green);color:var(--white)}.pagination[_ngcontent-%COMP%]{margin-top:1rem}.text-grey[_ngcontent-%COMP%]{color:#817979}.status[_ngcontent-%COMP%]{position:relative;margin-left:.5rem;padding-left:.5rem;color:var(--black-white)}.status[_ngcontent-%COMP%]:before{content:"";position:absolute;left:-8px;top:6px;height:10px;width:10px;border-radius:50%}.status.status--reject[_ngcontent-%COMP%]:before{background-color:#ff6060}.status.status--complete[_ngcontent-%COMP%]:before{background-color:#2ba531}.status.status--pending[_ngcontent-%COMP%]:before{background-color:#e2af00}.status.status--inprogress[_ngcontent-%COMP%]:before{background-color:#03a9f4}']});const S=[{path:"app-history",component:l}];class c{}c.\u0275fac=function(n){return new(n||c)},c.\u0275mod=t.oAB({type:c}),c.\u0275inj=t.cJS({imports:[g.Bz.forChild(S),g.Bz]});var P=i(3493);class s{}s.\u0275fac=function(n){return new(n||s)},s.\u0275mod=t.oAB({type:s}),s.\u0275inj=t.cJS({imports:[u.ez,c,d.JX,P.d]})}}]);