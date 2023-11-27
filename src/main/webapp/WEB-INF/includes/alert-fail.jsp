<div class="fixed top-36 left-8" id="statusAlert">
    <div class="bg-red-400 bg-opacity-50 hover:bg-opacity-100 transition-all duration-150 p-6 rounded-lg shadow-lg shadow-slate-200">
        <div class="flex items-center">
            <div class="p-2 bg-white rounded-full">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                </svg>
            </div>
            <div class="ml-4">
                <h2 class="text-white text-lg font-extrabold">Failed</h2>
                <p class="text-white text-sm font-semibold"><c:out value="${alert.getMessage()}" /></p>
            </div>
        </div>
    </div>
</div>