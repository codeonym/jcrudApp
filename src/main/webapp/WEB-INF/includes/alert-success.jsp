<div class="fixed top-36 left-8" id="statusAlert">
    <div class="bg-emerald-400 bg-opacity-50 hover:bg-opacity-100 transition-all duration-150 p-6 rounded-lg shadow-lg shadow-slate-200">
        <div class="flex items-center">
            <div class="p-2 bg-white rounded-full">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-emerald-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6-7a6 6 0 0 1 11.8-1h2.2a8 8 0 1 0-15.99 0h2.2A6 6 0 0 1 6 6zm10 4h2m-2 0h-2m0 0a12 12 0 1 1-11.74 0H4.73a10 10 0 1 0 19.54 0h-2.01z"></path>
                </svg>
            </div>
            <div class="ml-4">
                <h2 class="text-white text-lg font-extrabold">Succeed</h2>
                <p class="text-white text-sm font-semibold"><c:out value="${alert.getMessage()}" /></p>
            </div>
        </div>
    </div>
</div>