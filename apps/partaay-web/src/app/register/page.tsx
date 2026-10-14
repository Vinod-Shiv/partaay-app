"use client";
import Link from "next/link";
import { FormEvent, useState } from "react";

export default function RegisterPage() {
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (password !== confirmPassword) return alert("Passwords do not match.");
    alert("Registration API will be connected to Spring Boot next.");
  };
  return (
    <main className="auth-page"><div className="auth-card">
      <Link href="/" className="brand">Partaay</Link><h1>Create your account 🎊</h1>
      <p className="subtitle">Join Partaay and start discovering events around you.</p>
      <div className="social-buttons">
        <button type="button" className="social-button" onClick={() => alert("Google registration coming next.")}>Sign up with Google</button>
        <button type="button" className="social-button" onClick={() => alert("Facebook registration coming next.")}>Sign up with Facebook</button>
      </div>
      <div className="divider"><span>or register with email</span></div>
      <form onSubmit={handleSubmit}>
        <div className="form-group"><label htmlFor="name">Full name</label><input id="name" type="text" placeholder="Your full name" required /></div>
        <div className="form-group"><label htmlFor="email">Email address</label><input id="email" type="email" placeholder="you@example.com" required /></div>
        <div className="form-group"><label htmlFor="password">Password</label><div className="password-field">
          <input id="password" type={showPassword ? "text" : "password"} placeholder="Create a password" value={password} onChange={e => setPassword(e.target.value)} minLength={8} required />
          <button type="button" className="show-password" onClick={() => setShowPassword(!showPassword)}>{showPassword ? "Hide" : "Show"}</button>
        </div></div>
        <div className="form-group"><label htmlFor="confirmPassword">Confirm password</label><input id="confirmPassword" type="password" placeholder="Confirm your password" value={confirmPassword} onChange={e => setConfirmPassword(e.target.value)} required /></div>
        <label className="terms"><input type="checkbox" required /><span>I agree to the <Link href="#">Terms of Service</Link> and <Link href="#">Privacy Policy</Link>.</span></label>
        <button type="submit" className="primary-button">Create Account</button>
      </form>
      <p className="bottom-text">Already have an account? <Link href="/login">Sign in</Link></p>
    </div></main>
  );
}