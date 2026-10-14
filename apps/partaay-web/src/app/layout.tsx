import "./globals.css";
export const metadata = { title: "Partaay", description: "Discover, create and enjoy events with Partaay." };
export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) { return <html lang="en"><body>{children}</body></html>; }