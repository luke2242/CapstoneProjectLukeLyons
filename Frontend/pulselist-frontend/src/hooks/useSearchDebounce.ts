import { useEffect, useState } from "react";

// Debouncing ensures that our search is not updated on every keystroke
// The delay number here is based on milliseconds
export const useSearchDebounce = <T>(value: T, delay: number = 500) => {

    const [debouncedValue, setDebouncedValue] = useState<T>(value);

    useEffect(() => {

        const timeout = setTimeout(() => {

            setDebouncedValue(value);
        }, delay)


        // Timeout is cleared and we return to 500 milliseconds again
        return () => clearTimeout(timeout);
    }, [value, delay]);

    return debouncedValue;
} 