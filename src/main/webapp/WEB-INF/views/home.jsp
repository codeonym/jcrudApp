<!-- Main Content -->
<main class="bg-white mt-8">
    <div class="container mx-auto mt-8 flex flex-col md:flex-row">
        <aside class="text-slate-400 p-4 flex-shrink-0  md:w-80 lg:w-96 md:border-r-2 md:border-slate-100">
            <img class="w-16 ml-8 mb-4 mt-2" src="resources/assets/logo fso.png" alt="University FSO Logo" />
            <div>
                <c:if test="${not empty sessionScope.user}">
                    <p class="text-2xl font-extrabold py-4">Welcome, ${sessionScope.user.getUsername()}!</p>
                </c:if>
            </div>
            <nav class="flex flex-col items-center justify-center divide-y-2 divide-slate-100">
                <a href="#" onclick="showSection('viewStudents')" class="w-full block py-4 px-8 hover:bg-slate-100">
                    View Students
                </a>
                <a href="#" onclick="showSection('addStudent')" class="w-full block py-4 px-8 hover:bg-slate-100">
                    Add Student
                </a>
                <a href="#" onclick="showSection('searchStudent')" class="w-full block py-4 px-8 hover:bg-slate-100">
                    Search Student
                </a>
                <a href="#" onclick="showSection('addAbsence')" class="w-full block py-4 px-8 hover:bg-slate-100">
                    Add Absence
                </a>
                <a href="#" onclick="showSection('addNotes')" class="w-full block py-4 px-8 hover:bg-slate-100">
                    Add Notes
                </a>
            </nav>
            <form action="logout" method="post" class="mt-4">
                <input type="hidden" name="action" value="logout" />
                <button type="submit" class="bg-red-400 py-2 px-8 rounded-full hover:shadow-lg text-white transform hover:scale-105 transition-transform duration-300">
                    Logout
                </button>
            </form>
        </aside>

        <!-- Main Content -->
        <div class="flex-1 p-8 min-h-screen text-slate-500">

            <%-- NOTICE: THOSE INCLUDES ARE INCLUDE AT COMPILE TIME --%>
            <%@ include file="../includes/viewStudents.jsp" %>
            <%@ include file="../includes/addStudent.jsp" %>
            <%@ include file="../includes/searchStudent.jsp" %>
            <%@ include file="../includes/addAbsence.jsp" %>
            <%@ include file="../includes/addNotes.jsp" %>
        </div>
    </div>
</main>
<!-- JavaScript for Sidebar Functionality -->
<script src="resources/js/home.js"></script>
</body>
</html>