"use client";
import Link from "next/link";
import { FormEvent, useState } from "react";

export default function LoginPage() {
  const [showPassword, setShowPassword] = useState(false);
  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    alert("Login API will be connected to Spring Boot next.");
  };
  return (
    <main className="auth-page"><div className="auth-card">
      <Link href="/" className="brand">Partaay</Link>
      <h1>Welcome back 🎉</h1>
      <p className="subtitle">Sign in to discover events, parties, and experiences near you.</p>
      <div className="social-buttons">
        <button type="button" className="social-button" onClick={() => alert("Google login will be connected later.")}>Continue with Google</button>
        <button type="button" className="social-button" onClick={() => alert("Facebook login will be connected later.")}>Continue with Facebook</button>
      </div>
      <div className="divider"><span>or continue with email</span></div>
      <form onSubmit={handleSubmit}>
        <div className="form-group"><label htmlFor="email">Email address</label><input id="email" type="email" placeholder="you@example.com" required /></div>
        <div className="form-group"><label htmlFor="password">Password</label><div className="password-field">
          <input id="password" type={showPassword ? "text" : "password"} placeholder="Enter your password" required />
          <button type="button" className="show-password" onClick={() => setShowPassword(!showPassword)}>{showPassword ? "Hide" : "Show"}</button>
        </div></div>
        <div className="form-options"><label className="remember"><input type="checkbox" />Remember me</label><Link href="/forgot-password">Forgot password?</Link></div>
        <button type="submit" className="primary-button">Sign In</button>
      </form>
      <p className="bottom-text">New to Partaay? <Link href="/register">Create an account</Link></p>
    </div></main>
  );
}